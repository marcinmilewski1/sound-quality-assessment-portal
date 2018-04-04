import {Component, OnInit, Output, EventEmitter, Input, ViewChild, AfterViewInit} from "@angular/core";
import {DomSanitizer} from "@angular/platform-browser";
import {ArrayUtils} from "../../shared/utils/ArrayUtils";
import {AudioPlayerComponent} from "../audio-player/audio-player.component";
import {Subject} from "rxjs";
import {RepeatableTest} from "../base/RepeatableTest";
import {MdRadioButton} from "@angular/material";
import {Sample} from "../base/Sample";

@Component({
  selector: 'abx-test-player',
  templateUrl: './abx-test-player.component.html',
  styleUrls: ['./abx-test-player.component.scss']
})
export class AbxTestPlayerComponent implements OnInit, RepeatableTest {

  @Input()
  private test;
  @Input() private iteration: number;
  @Input() private commandEmmiter: Subject<string>;
  @ViewChild(AudioPlayerComponent) audioPlayer: AudioPlayerComponent;
  @ViewChild('bRadioButton') bRadioButton: MdRadioButton;
  @ViewChild('aRadioButton') aRadioButton: MdRadioButton;

  @Output()
  private onFinish = new EventEmitter<any>();

  private selectedSample: string;

  private isAplayed = false;
  private isBplayed = false;
  private isXplayed = false;
  private blindSampleKey: string;
  private samplesKeyMap: any[] = [];
  private resultGroup: any;
  private submitted = false;

  private samples: Sample[] = [];

  constructor(private domSanitizer: DomSanitizer) {
    this.resultGroup = {
      "type": "ABX",
      "results": []
    };
  }

  public clearContext() {
    this.isAplayed = false;
    this.isBplayed = false;
    this.isXplayed = false;
    this.samplesKeyMap = [];
    this.selectedSample = null;
    this.submitted = false;
    this.aRadioButton.checked = false;
    this.bRadioButton.checked = false;
    this.aRadioButton.disabled = true;
    this.bRadioButton.disabled = true;
  }

  ngOnInit() {
    this.commandEmmiter.subscribe(event => {
      if (event === 'iteration') {
        this.audioPlayer.stop();
        this.clearContext();
        this.initSamplesOrder();
      }
    });
    this.aRadioButton.disabled = true;
    this.bRadioButton.disabled = true;
    this.initSamplesOrder();
  }

  public initSamplesOrder() {
    if (Math.random() < 0.5) {
      this.blindSampleKey = 'A';
    }
    else {
      this.blindSampleKey = 'B';
    }
    console.log("Samples");
    console.log(this.samples);

    let samplesKeyShuffled = ArrayUtils.shuffle(this.test.samples.map((s) => s.sampleKey));
    console.log("Shuffled:");
    console.log(samplesKeyShuffled);

    this.samplesKeyMap = this.test.samples
      .sort((a, b) => {
        let lca = a.sampleKey.toLowerCase(), lcb = b.sampleKey.toLowerCase();
        return lca > lcb ? 1 : lca < lcb ? -1 : 0;
      })
      .map((sample, index) => {
        console.log(samplesKeyShuffled[index] + "->" + sample.sampleKey);
        return {
          currentSampleKey: samplesKeyShuffled[index],
          originalSampleKey: sample.sampleKey,
        };
      }).sort((a, b) => {
        let lca = a.currentSampleKey.toLowerCase(), lcb = b.currentSampleKey.toLowerCase();
        return lca > lcb ? 1 : lca < lcb ? -1 : 0;
      });
    console.log(this.samplesKeyMap);
    console.log("BLIND KEY: " + this.blindSampleKey);

    this.samples = this.test.samples.map((s) => {
      return {
        key: s.sampleKey,
        url: (this.test.samples as any[])
          .find((sample) => sample.sampleKey === this.samplesKeyMap
            .find((v) => v.currentSampleKey === s.sampleKey).originalSampleKey).blobUrl
      }
    });

    this.samples.push({
      key: 'X',
      url: (this.test.samples as any[])
        .find((sample) => sample.sampleKey === this.samplesKeyMap
          .find((v) => v.currentSampleKey === this.blindSampleKey).originalSampleKey).blobUrl
    });
  }

  public onSampleSelectToPlay(sampleKey: string) {
    this.audioPlayer.play(sampleKey);
    if (sampleKey === 'A') {
      this.isAplayed = true;
    }
    if (sampleKey === 'B') {
      this.isBplayed = true;
    }
    if (sampleKey === 'X') {
      sampleKey = this.blindSampleKey;
      this.isXplayed = true;
      this.aRadioButton.disabled = false;
      this.bRadioButton.disabled = false;
    }
  }

  onTestAnswer(sampleKey: string) {
    if (this.isXplayed) {
      this.selectedSample = sampleKey;
    }
  }

  public onSubmit() {
    let result = {
      abxBlindSampleKey: this.samplesKeyMap.find((v) => v.currentSampleKey === this.blindSampleKey).originalSampleKey,
      abxResult: this.samplesKeyMap.find((v) => v.currentSampleKey === this.selectedSample).originalSampleKey,
      iteration: this.iteration
    };
    this.resultGroup.results.push(result);
    console.log(result);
    this.submitted = true;
    this.onFinish.emit(this.resultGroup);
  }


}
