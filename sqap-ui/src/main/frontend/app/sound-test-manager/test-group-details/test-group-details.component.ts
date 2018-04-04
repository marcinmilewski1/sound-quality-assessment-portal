import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Http, Headers} from "@angular/http";
import {SoundTestGroup} from "../domain/SoundTestGroup";
import {AuthService} from "../../shared/auth/auth.service";
import {UrlUtils} from "../../shared/utils/UrlUtils";
import {ArrayUtils} from "../../shared/utils/ArrayUtils";
import {TestGroupService} from "../../shared/test/group/test-group.service";
import {FilesDownloadService} from "../../shared/files/files-download.service";
import {TestService} from "../../shared/test/single/test.service";
import {StatUtils} from "../../shared/stat/StatUtils";
import {DomSanitizer} from "@angular/platform-browser";
import {ResultsViewCreator} from "../tools/results-view-creator";
declare var mctad: any;

@Component({
  selector: 'test-group-details',
  templateUrl: './test-group-details.component.html',
  styleUrls: ['./test-group-details.component.scss']
})
export class TestGroupDetailsComponent implements OnInit {

  private testGroup: any;
  private exported = false;
  private tests: any[];
  private chiSquareProbabilities = StatUtils.getChiSquaredDistributionValues1DF();
  private jsonUriResults: string;
  private csvUriResults: string;

  constructor(private route: ActivatedRoute, private testService: TestService, private testGroupService: TestGroupService, private fileDownloadService: FilesDownloadService, private domSanitizer: DomSanitizer) {
  }

  ngOnInit() {
    let id = this.route.snapshot.params['id'];
    let URL = `/api/groups/${id}`;

    this.testGroupService.getOne(id)
      .subscribe(data => {
          this.testGroup = data;
          this.fetchTests(this.testGroup._links.tests.href);
        },
        err => {
          console.log("ERR");
        }
      );
  }

  public markAsFinished(id: number) {
    this.testGroupService.markAsFinished(id).subscribe(
      data => {
        this.testGroup.finished = true;
      }
    );
  }

  private fetchTests(href: string) {
    this.testGroupService.getTests(href, "all").subscribe(
      data => {
        this.tests = data;
        (this.tests as any[]).forEach((t, index, array) => {
            array[index].samples = t.samples.sort((a, b) => {
              let lca = a.key.toLowerCase(), lcb = b.key.toLowerCase();
              return lca > lcb ? 1 : lca < lcb ? -1 : 0;
            });
            (array[index].samples as any[]).forEach((sample) => {
              sample.fileName = decodeURIComponent(sample.fileName);
            });
          }
        );
        console.log(this.tests);
      }
    )
  }


  exportResults(test: any) {
    let data = ResultsViewCreator.createFriendlyResultsView(test);
    test.jsonUriResults = this.domSanitizer.bypassSecurityTrustResourceUrl(this.fileDownloadService.exportAsJsonObject(data)) as string;
    test.csvUriResults = this.domSanitizer.bypassSecurityTrustResourceUrl(this.fileDownloadService.exportAsCsv(data, test.type)) as string;
    test.exported = true;
  }


  onTabSelectChangeHandler(event: any) {
    if (event.index === 1) { // tab results
      let test = event.test;
      console.log(test);
      this.testService.getResults(test._links.getResultsFormatted.href).subscribe(data => {
        let selectedTest = this.tests.find((t) => t.id === test.id);
        let formattedResults = data;
        console.log(formattedResults);
        if (selectedTest.type === 'MUSHRA') {
          console.log("MUSHRA");
          for (let result of formattedResults) {
            if (!result.sampleStdDeviation) {
              result.sampleStdDeviation = 0;
            }
            let sampleMean95ConfidenceInterval =
              mctad.confidenceIntervalOnTheMean(result.mean, result.sampleStdDeviation, result.totalAnswers, 0.05);
            result.sampleMean95ConfidenceInterval = sampleMean95ConfidenceInterval;
            let sampleMean99ConfidenceInterval =
              mctad.confidenceIntervalOnTheMean(result.mean, result.sampleStdDeviation, result.totalAnswers, 0.01);
            result.sampleMean99ConfidenceInterval = sampleMean99ConfidenceInterval;
          }
        }
        if (selectedTest.type === 'ABX') {
          formattedResults.pearsonsChiSquareValue =
            StatUtils.abxPearsonsChiSquaredValue(formattedResults.correct_answers, formattedResults.answers);

          console.log(formattedResults);
        }
        selectedTest.formattedResults = formattedResults;
      });
    }
  }

  onDeleteButtonHandler(id: number) {
    this.testGroupService.delete(id).subscribe(data => {
      console.log("Delete successful");
    });
  }
}
