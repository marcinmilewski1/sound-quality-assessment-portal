import {Injectable} from '@angular/core';
import {Http, ResponseContentType} from "@angular/http";
import {AuthService} from "../auth/auth.service";
import {UrlUtils} from "../utils/UrlUtils";
import {Observable} from "rxjs";
var json2csv = require('json2csv');

@Injectable()
export class FilesDownloadService {

  constructor(private http: Http, private authsService: AuthService) {
  }

  getSampleFiles(sampleDescriptors: any[]): Observable<SampleFile>[] {
    let observablesArray: Observable<SampleFile>[] = [];
    for (let sampleDesc of sampleDescriptors) {
      let URL = sampleDesc._links.file.href;
      URL = UrlUtils.removePrefix(URL);

      let fileContent: any;
      let sampleKey = sampleDesc.key;
      let fileName = sampleDesc.fileName;

      observablesArray.push(this.http.get(URL, {
        headers: this.authsService.getAuthorizationHeaders(),
        responseType: ResponseContentType.Blob
      })
        .map(res => new SampleFile(sampleKey, fileName, res.blob())
        ));
    }
    return observablesArray;
  }

  getSampleFilesAsMergedObservable(sampleDescriptors: any[]): Observable<SampleFile>[] {
    let observablesArray: Observable<SampleFile>[] = [];
    for (let sampleDesc of sampleDescriptors) {
      let URL = sampleDesc._links.file.href;
      URL = UrlUtils.removePrefix(URL);

      let fileContent: any;
      let sampleKey = sampleDesc.key;
      let fileName = sampleDesc.fileName;

      observablesArray.push(this.http.get(URL, {
        headers: this.authsService.getAuthorizationHeaders(),
        responseType: ResponseContentType.Blob
      })
        .map(res => new SampleFile(sampleKey, fileName, res.blob())
        ));
    }
    return observablesArray;
  }

  exportAsJsonObject(data: any): string {
    let json = JSON.stringify(data);
    let blob = new Blob([json], {type: "application/json"});
    return URL.createObjectURL(blob);
  }

  exportAsCsv(data: any, testType: string): string {
    let json = JSON.stringify(data);
    let singleResult = {} as any;
    let resultArray = [];

    singleResult.name = data.name;
    singleResult.type = data.type;
    if (testType === 'ABX') {
      for (let resultGroup of data.iterationGroup) {
        singleResult.index = resultGroup.index;
        singleResult.tester = resultGroup.tester;
        singleResult.creationDate = resultGroup.creationDate;
        singleResult.isHearingDefect = resultGroup.isHearingDefect;
        singleResult.age = resultGroup.age;
        singleResult.sex = resultGroup.sex;
        for (let iterationResult of resultGroup.results) {
          singleResult.iteration = iterationResult.iteration;
          singleResult.answer = iterationResult.result.answer;
          singleResult.correct = iterationResult.result.correct;
          resultArray.push(JSON.parse(JSON.stringify(singleResult)));
        }
      }
    }
    if (testType === 'MUSHRA') {
      for (let resultGroup of data.iterationGroup) {
        singleResult.index = resultGroup.index;
        singleResult.tester = resultGroup.tester;
        singleResult.creationDate = resultGroup.creationDate;
        singleResult.isHearingDefect = resultGroup.isHearingDefect;
        singleResult.age = resultGroup.age;
        singleResult.sex = resultGroup.sex;
        for (let iterationResult of resultGroup.results) {
          singleResult.iteration = iterationResult.iteration;
          for (let result of iterationResult.result) {
            singleResult.sample = result.sample;
            singleResult.value = result.value;
            resultArray.push(JSON.parse(JSON.stringify(singleResult)));
          }
        }
      }
    }
    let csv = json2csv({data: resultArray});
    let blob = new Blob([csv], {type: "text/csv"});
    return URL.createObjectURL(blob);
  }

}


export class SampleFile {
  sampleKey: string;
  fileName: string;
  fileContent: any;
  blobUrl: string;

  constructor(sampleKey: string, fileName: string, fileContent: Blob) {
    this.sampleKey = sampleKey;
    this.fileName = fileName;
    this.fileContent = fileContent;
    this.blobUrl = URL.createObjectURL(this.fileContent);
  }
}
