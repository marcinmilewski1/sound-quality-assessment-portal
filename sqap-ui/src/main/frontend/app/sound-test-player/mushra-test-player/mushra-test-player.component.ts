import {Component, OnInit, Output, Input, EventEmitter, QueryList, ViewChildren, ViewChild} from "@angular/core";
import {ChangeEvent, SampleSliderComponent} from "../sample-slider/sample-slider/sample-slider.component";
import {DomSanitizer} from "@angular/platform-browser";
import {TestService} from "../../shared/test/single/test.service";
import {ArrayUtils} from "../../shared/utils/ArrayUtils";
import {AudioPlayerComponent} from "../audio-player/audio-player.component";
import {RepeatableTest} from "../base/RepeatableTest";
import {Subject} from "rxjs";
import {Sample} from "../base/Sample";

@Component({
  selector: 'mushra-test-player',
  templateUrl: './mushra-test-player.component.html',
  styleUrls: ['./mushra-test-player.component.scss']
})
export class MushraTestPlayerComponent implements OnInit, RepeatableTest {

  @Input() private commandEmmiter: Subject<string>;
  @Input() private test;
  @Input() private iteration: number;
  @ViewChild(AudioPlayerComponent) audioPlayer: AudioPlayerComponent;
  @ViewChildren(SampleSliderComponent) private sampleSliders: QueryList<SampleSliderComponent>;

  private samplesKeyMap: any[] = [];
  private samplesKeyListened: any[] = [];
  private isEverySamplesListened = false;
  private playingSampleName: string;
  private resultGroup: any;
  private submitted = false;

  private testSamplesDisable = true;

  @Output() private onFinish = new EventEmitter<any>();
  private samples: Sample[] = [];

  constructor(private domSanitizer: DomSanitizer, private testService: TestService) {
    this.resultGroup = {
      "type": "MUSHRA",
      "results": []
    };
  }

  ngOnInit() {
    this.commandEmmiter.subscribe(event => {
      if (event === 'iteration') {
        this.audioPlayer.stop();
        this.clearContext();
        this.initSamplesOrder();
      }
    });
    this.initSamplesOrder();
  }

  clearContext() {
    this.samplesKeyMap = [];
    this.samplesKeyListened = [];
    this.playingSampleName = '';
    this.isEverySamplesListened = false;
    this.testSamplesDisable = true;
    this.submitted = false;
  }

  initSamplesOrder() {
    let samples = this.test.samples;
    console.log(samples.length);
    let samplesKeyShuffled = ArrayUtils.shuffle(samples.map((s) => s.sampleKey));
    console.log("Shuffled:");
    console.log(samplesKeyShuffled);

    console.log("Samples");
    console.log(this.samples);

    this.samplesKeyMap = samples
      .map((sample, index) => {
        console.log(samplesKeyShuffled[index] + "->" + sample.sampleKey);
        return {
          currentSampleKey: samplesKeyShuffled[index],
          originalSampleKey: sample.sampleKey,
          value: 0
        };
      }).sort((a, b) => {
        let lca = a.currentSampleKey.toLowerCase(), lcb = b.currentSampleKey.toLowerCase();
        return lca > lcb ? 1 : lca < lcb ? -1 : 0;
      });

    this.samples = this.test.samples.map((s) => {
      return {
        key: s.sampleKey,
        url: (this.test.samples as any[])
          .find((sample) => sample.sampleKey === this.samplesKeyMap
            .find((v) => v.currentSampleKey === s.sampleKey).originalSampleKey).blobUrl
      };
    });

    this.samples.push({
      key: 'REF',
      url: (this.test.samples as any[])
        .find((sample) => sample.sampleKey === 'A').blobUrl
    });
  }

  onSubmit() {
    let answers = this.samplesKeyMap.map((v) => {
      return {
        key: v.originalSampleKey,
        value: v.value
      }
    });
    let result = {
      type: "MUSHRA",
      sampleResults: answers,
      iteration: this.iteration
    };
    this.resultGroup.results.push(result);
    this.submitted = true;
    this.onFinish.emit(this.resultGroup);
  }

  onSampleSliderChangeHandler(event: ChangeEvent) {
    this.samplesKeyMap.find((v) => v.currentSampleKey === event.key).value = event.value;
    console.log(event);
  }

  onSampleSelectToPlay(sampleKey: string) {
    this.audioPlayer.play(sampleKey);
    if (sampleKey != this.playingSampleName) {
      this.disableSliders();
      this.playingSampleName = sampleKey;
      if (sampleKey === 'REF') {
        this.testSamplesDisable = false;
      }
      else {
        this.sampleSliders.find((s) => s.sampleKey === sampleKey).setDisabled(false);
        this.addToHeard(sampleKey);
      }
    }
  }

  private addToHeard(sampleKey: string) {
    if (this.samplesKeyListened.find((k) => k === sampleKey) == null) {
      this.samplesKeyListened.push(sampleKey);
      if (this.samplesKeyListened.length === this.samplesKeyMap.length) {
        console.log(this.samplesKeyListened);
        console.log(this.samplesKeyMap);
        this.isEverySamplesListened = true;
      }
    }
  }

  private disableSliders() {
    this.sampleSliders.forEach(s => s.setDisabled(true));
  }

}
