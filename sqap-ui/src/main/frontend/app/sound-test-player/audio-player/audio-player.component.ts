import {Component, OnInit, Input, ViewChild, ElementRef, AfterViewInit, OnDestroy, NgZone} from '@angular/core';
import {DomSanitizer, SafeUrl, SafeResourceUrl} from "@angular/platform-browser";
import {Subject, Observable, Subscription} from "rxjs";
// import * as $ from 'jquery'
// declare var $:JQueryStatic;
declare var jQuery: JQueryStatic;
var $ = jQuery;
import 'jquery-ui'
import * as Rx from 'rxjs/Rx';
import {Sample} from "../base/Sample";

@Component({
  selector: 'audio-player',
  templateUrl: './audio-player.component.html',
  styleUrls: ['./audio-player.component.scss']
})
export class AudioPlayerComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('player') private playerRef: ElementRef;
  @ViewChild('sliderRange') sliderRange: ElementRef;
  @ViewChild('volumeRange') volumeRange: ElementRef;
  @ViewChild('counter') counter: ElementRef;
  @ViewChild('startTime') startTime: ElementRef;
  @ViewChild('endTime') endTime: ElementRef;
  @ViewChild('progressBar') progressBar: ElementRef;

  private _samples: Sample[];
  private audioObjects: AudioObject[] = [];
  private state: State;
  private params: Params;
  private currentAudioObject: AudioObject;
  range: [number, number] = [0, 100];
  private secondTimer: Observable<any>;
  private secondTimerSubscribtion: Subscription;
  private rightThresholdTime: number;
  private currentAudioObjectDup: AudioObject;
  private prescalerIndex: number = 1;

  @Input()
  set samples(samples: Sample[]) {
    if (this.audioContext == null) {
      this.loadContext();
    }
    else {
      this.clearContext();
    }
    let that = this;
    for (let sample of samples) {
      that.createAudioObject(sample, sample.key);
      that.createAudioObject(sample, sample.key + "_DUP");
    }
  }

  private createAudioObject(sample, key) {
    let audioElement = document.createElement("audio") as HTMLAudioElement;
    audioElement.src = sample.url;
    audioElement.loop = false;
    let source = this.audioContext.createMediaElementSource(audioElement);
    let localGainNode = this.audioContext.createGain();
    localGainNode.gain.value = 0.0;
    source.connect(localGainNode);
    localGainNode.connect(this.mainGainNode);
    this.mainGainNode.connect(this.audioContext.destination);
    this.audioObjects.push({
      key: key,
      audioSource: source,
      audioElement: audioElement,
      gainNode: localGainNode
    });
  }

  private _name: string;

  private audioContext: AudioContext;
  private mainGainNode: GainNode;


  constructor(private zone: NgZone) {
    this.state = {
      autoplay: false,
      loopback: true,
      synchronousMode: true,
      muted: false,
    };
    this.params = {
      fadeInTime: 0.06,
      fadeOutTime: 0.06,
      intervalTime: 10,
      domUpdatePrescaler: 5
    }
  }

  ngOnInit() {
    this.secondTimer = Rx.Observable
      .interval(this.params.intervalTime /* ms */);
  }

  ngOnDestroy(): void {
    // to#do destroy setInterval
    this.audioObjects.forEach((a) => a.audioSource.disconnect());
  }

  private loadContext() {
    this.audioContext = new AudioContext();
    this.mainGainNode = this.audioContext.createGain();

  }

  ngAfterViewInit(): void {
    this.createDoubleRangeSlider();
  }

  private clearContext() {
    this.audioObjects.forEach((a) => a.audioSource.disconnect());
    this.audioObjects = [];
    this.currentAudioObject = null;
    this.currentAudioObjectDup = null;
    this.name = null;
    this.startTime.nativeElement.innerHTML = "00:00s";
    this.counter.nativeElement.innerHTML = "00:00s";
    this.endTime.nativeElement.innerHTML = "00:00s";
    jQuery(this.progressBar.nativeElement).attr('aria-valuenow', 0).css('width', 0 + "%");
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  setLoop(value: boolean) {
    this.state.loopback = value;
  }

  setSynchronousMode(value: boolean) {
    this.state.synchronousMode = value;
  }

  public tooglePlay() {
    this.currentAudioObject.audioElement.paused ? this.resume() : this.pause();
  }

  public pause() {
    this.currentAudioObject.audioElement.pause();
  }

  public resume() {
    this.currentAudioObject.audioElement.play();
  }

  public stop() {

  }

  public play(key: string) {
    // console.time('play');
    this.zone.runOutsideAngular(() => {
      if (this.currentAudioObject == null) {
        let currentAudioObject = this.audioObjects.find(v => v.key === key);
        currentAudioObject.gainNode.gain.linearRampToValueAtTime(1,
          this.audioContext.currentTime + this.params.fadeInTime);
        currentAudioObject.audioElement.play();
        this.currentAudioObject = currentAudioObject;
        this.currentAudioObjectDup = this.audioObjects.find(v => v.key === key + "_DUP");
        this.leftThresholdHandler(this.range[0]);
        this.setRightThresholdTime(this.range[1]);
        this.secondTimerSubscribtion = this.secondTimer.subscribe(data => {
          this.onTimeUpdateEventHandler();
        });
      }
      else if (key === this.currentAudioObject.key) {
        this.tooglePlay();
      }
      else {
        while (1) { // avoid mixins with setInterval callbacks
          let nextAudioObject = this.audioObjects.find(v => v.key === key);
          let currentAudioObject = this.currentAudioObject;
          let currentTime = this.range[0] / 100 * nextAudioObject.audioElement.duration + 's';
          let endTime = this.range[1] / 100 * nextAudioObject.audioElement.duration + 's';

          if (this.state.synchronousMode == true) {
            let ctxCurrentTime = this.audioContext.currentTime;
            // console.log(ctxCurrentTime);
            nextAudioObject.audioElement.currentTime = currentAudioObject.audioElement.currentTime;
            nextAudioObject.gainNode.gain.linearRampToValueAtTime(1.0, ctxCurrentTime + this.params.fadeInTime);
            currentAudioObject.gainNode.gain.linearRampToValueAtTime(0.0, ctxCurrentTime + this.params.fadeOutTime);
            nextAudioObject.audioElement.play();
            // this.doDelayedPause(currentAudioObject.audioElement, this.params.fadeOutTime * 1000);
          }
          else {
            // no synchro
            nextAudioObject.audioElement.currentTime = this.range[0] / 100 * nextAudioObject.audioElement.duration;
            nextAudioObject.gainNode.gain.linearRampToValueAtTime(1, this.audioContext.currentTime + this.params.fadeInTime);
            currentAudioObject.gainNode.gain.linearRampToValueAtTime(0.0, this.audioContext.currentTime + this.params.fadeOutTime);
            nextAudioObject.audioElement.play();
            // this.doDelayedPause(currentAudioObject.audioElement, this.params.fadeOutTime * 1000);
          }
          this.currentAudioObject = nextAudioObject;
          this.currentAudioObjectDup = this.audioObjects.find(v => v.key === key + "_DUP");
          this.setRightThresholdTime(this.range[1]);
          break;
        }
      }
    });
    setTimeout(() => this.name = key, 120);
    // this.name = key;
    // console.timeEnd('play');
  }

  private toogleMute() {
  }

  private copyAudioBufferSourceNode(audioBufferSourceNode: AudioBufferSourceNode): AudioBufferSourceNode {
    let copy = this.audioContext.createBufferSource();
    copy.buffer = audioBufferSourceNode.buffer;
    (copy as any).started = false;
    return copy;
  }

  private createDoubleRangeSlider() {
    let that = this;
    jQuery.noConflict();
    jQuery(function () {
      jQuery(that.sliderRange.nativeElement).slider({
        range: true,
        step: 0.1,
        min: 0.0,
        max: 100.0,
        values: [0.0, 100.0],
        slide: function (event, ui) {
          if (ui.values[0] !== that.range[0]) {
            that.leftThresholdHandler(ui.values[0]);
          }
          if (ui.values[1] !== that.range[1]) {
            that.setRightThresholdTime(ui.values[1])
          }
          that.range = [ui.values[0], ui.values[1]];
        }
      });
      jQuery(that.volumeRange.nativeElement).slider({
        step: 0.01,
        min: 0.0,
        max: 1.0,
        value: 0.5,
        slide: function (event, ui) {
          that.mainGainNode.gain.value = ui.value;
        }
      });
    });
  }

  onTimeUpdateEventHandler() {
    if (this.currentAudioObject != null) {
      this.rightThresholdHandler();
      if (this.prescalerIndex++ === this.params.domUpdatePrescaler) {
        let currentTime = this.currentAudioObject.audioElement.currentTime;
        this.counter.nativeElement.innerHTML = currentTime.toFixed(2) + "s";
        let newProgress = 100 * currentTime / this.currentAudioObject.audioElement.duration;
        jQuery(this.progressBar.nativeElement).attr('aria-valuenow', newProgress).css('width', newProgress + "%");
        this.prescalerIndex = 1;
      }
    }
  }

  private rightThresholdHandler() {
    if (this.currentAudioObject.audioElement.currentTime >= this.rightThresholdTime) {
      while (1) { // avoid mixins with setInterval callbacks
        if (this.state.loopback) {
          this.currentAudioObjectDup.audioElement.currentTime = this.range[0] / 100 * this.currentAudioObject.audioElement.duration;
          this.currentAudioObject.gainNode.gain.linearRampToValueAtTime(0, this.audioContext.currentTime + this.params.fadeOutTime);
          this.currentAudioObjectDup.gainNode.gain.linearRampToValueAtTime(1, this.audioContext.currentTime + this.params.fadeInTime);
          this.currentAudioObjectDup.audioElement.play();
          this.doDelayedPause(this.currentAudioObject.audioElement, this.params.fadeInTime + this.params.fadeOutTime);

          let orignal = this.currentAudioObject;
          let dup = this.currentAudioObjectDup;

          dup.key = orignal.key;
          orignal.key += "_DUP";

          this.currentAudioObjectDup = orignal;
          this.currentAudioObject = dup;

        }
        else {
          this.currentAudioObject.audioElement.pause();
          this.currentAudioObject.audioElement.currentTime = this.range[0] / 100 * this.currentAudioObject.audioElement.duration;
        }
        break;
      }
    }
  }

  private leftThresholdHandler(rangeValue: number) {
    if (this.currentAudioObject != null) {
      let startTime = rangeValue / 100 * this.currentAudioObject.audioElement.duration;
      this.currentAudioObject.audioElement.currentTime = startTime;
      this.startTime.nativeElement.innerHTML = startTime.toFixed(2) + 's';
    }
  }

  private doDelayedPause(audioElement: HTMLAudioElement, delay: number) {
    setTimeout(audioElement.pause(), delay);
  }

  private setRightThresholdTime(rangeValue: number) {
    if (this.currentAudioObject != null) {
      let endTime = rangeValue / 100 * this.currentAudioObject.audioElement.duration;
      this.rightThresholdTime = endTime - this.params.fadeOutTime - (this.params.intervalTime / 1000);
      this.endTime.nativeElement.innerHTML = endTime.toFixed(2) + 's';
    }
  }

  private onVolumeChange(value: number) {
    this.mainGainNode.gain.value = value;
  }


}


export interface AudioObject {
  key: string,
  audioSource: MediaElementAudioSourceNode
  audioElement: HTMLAudioElement
  gainNode: GainNode
}

interface State {
  autoplay: boolean;
  muted: boolean;
  loopback: boolean;
  synchronousMode: boolean;
}

interface Params {
  fadeInTime: number,  // s
  fadeOutTime: number, // s
  intervalTime: number // ms
  domUpdatePrescaler: number
}

