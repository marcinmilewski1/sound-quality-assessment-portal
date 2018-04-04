import {Component, OnInit, NgZone, Output, EventEmitter, Input, ElementRef, ViewChild} from '@angular/core';
import {AuthService} from "../shared/auth/auth.service";
import {UploadedFile} from "ng2-uploader/src/services/ng2-uploader";
import {SimpleEvent} from "../shared/SimpleEvent";
import {RandomUtils} from "../shared/utils/RandomUtils";

@Component({
    selector: 'file-upload',
    templateUrl: './file-upload.component.html',
    styleUrls: ['./file-upload.component.scss'],
    providers: [AuthService]
})
export class FileUploadComponent implements OnInit {

    @Input()
    private id: string;

    private templateId: string;
    @Output()
    private onSuccessful = new EventEmitter<SimpleEvent>();
    @Output()
    private onError = new EventEmitter<SimpleEvent>();

    @ViewChild('fileLabel') fileLabel: ElementRef;

    private zone: NgZone;
    hasBaseDropZoneOver: boolean = false;
    options: Object;
    private progress: number = 0;
    private response: any = {};
    authService: AuthService;
    private progess = 0;

    handleUpload(data : UploadedFile): void {
        console.log("performing " + this.id);
        this.zone.run(() => {
            this.fileLabel.nativeElement.innerHTML = data.originalName;
            if (data && data.done) {
                console.log("data done");
                if (data.status === 200) {
                    let dataObj = JSON.parse(data.response);
                    console.log("data 200");
                    this.progress = Math.floor((data.progress as any).percent);
                    this.onSuccessful.emit(new SimpleEvent(this.id, dataObj["uuid"], data.status, data.statusText));
                }
                else {
                    this.onError.emit(new SimpleEvent(this.id, data.response, data.status, data.statusText));
                    this.progress = 0;
                    console.log("data other stat");
                }
            } else {
                this.progress = Math.floor((data.progress as any).percent - 15);
            }
        });
    }

    fileOverBase(e: any): void {
        this.hasBaseDropZoneOver = e;
    }

    constructor(authService: AuthService) {
        this.authService = authService;
    }

    ngOnInit() {
        console.log('file upload ID' + this.id);
        this.zone = new NgZone({ enableLongStackTrace: false });
        this.options = {
            url: 'zuul/api/files',
            authToken: this.authService.getAccessToken(),
            authTokenPrefix: 'Bearer',
            calculateSpeed: true,
            // filterExtensions: true,
            // allowedExtensions: ['audio/*']
        };
        this.templateId = RandomUtils.randomAlphanumeric(10);
    }

}
