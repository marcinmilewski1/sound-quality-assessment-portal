import {Injectable, Sanitizer} from '@angular/core';
import {DomSanitizer, SafeHtml, SafeStyle, SafeScript, SafeUrl, SafeResourceUrl} from "@angular/platform-browser";

@Injectable()
export class NoopDomSanitizerService extends DomSanitizer {
    sanitize(context: any, value: any): string;
    sanitize(context: any, value: string): string;
    sanitize(context: any, value): string {
        return value;
    }

    bypassSecurityTrustHtml(value: string): SafeHtml {
        return value;
    }

    bypassSecurityTrustStyle(value: string): SafeStyle {
        return value;
    }

    bypassSecurityTrustScript(value: string): SafeScript {
        return value;
    }

    bypassSecurityTrustUrl(value: string): SafeUrl {
        return value;
    }

    bypassSecurityTrustResourceUrl(value: string): SafeResourceUrl {
        return value;
    }

    constructor() {
        super();
    }

}
