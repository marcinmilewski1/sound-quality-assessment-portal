
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {AuthService} from "../../auth/auth.service";
import {Http, Response, Headers} from "@angular/http";
import {UrlUtils} from "../../utils/UrlUtils";
import {ArrayUtils} from "../../utils/ArrayUtils";

@Injectable()
export class TestGroupService {

    constructor(private authService: AuthService, private http: Http) {}

    public getOwn(projection?: string): Observable<any[]> {
        let URL = UrlUtils.addProjection('api/groups/search/findOwn', projection);
        return this.http.get(URL, {headers: this.getAuthHeaders()}).map(res => res.json());
    }

    public create(formData: FormData): Observable<Response> {
        let URL = 'api/groups';
        return this.http.post(URL, formData, {headers: this.getAuthHeaders()});
    }

    public getOne(id: number) : Observable<any> {
        let URL = `/api/groups/${id}`;
        return this.http.get(URL, {headers: this.getAuthHeaders()}).map(res => res.json());
    }

    public markAsFinished(id: number): Observable<Response> {
        let FINISH_URL = `/api/audio/tests/finish/${id}`;
        return this.http.post(FINISH_URL, {headers: this.getAuthHeaders()});
    }

    public getTests(href: string, projection?: string): Observable<any[]> {
        return this.getTestsNotMapped(href, projection).map(res => res['_embedded']).map(body => {
            let abxTests = body.abxTestEntities;
            let mushraTests = body.mushraTestEntities;
            let tests = ArrayUtils.concatSafe(abxTests, mushraTests);
            return (tests as any[]).sort((n1, n2) => n1.orderNumber - n2.orderNumber); // sort ascending by orderNo
        });
    }

    private getTestsNotMapped(href: string, projection?: string): Observable<any[]> {
        let URL = UrlUtils.removePrefix(href);
        URL = UrlUtils.addProjection(URL, projection);
        return this.http.get(URL, {headers: this.getAuthHeaders()}).map(res => res.json());
    }


    public getNotParticipated(projection?: string): Observable<Response> {
        let URL = UrlUtils.addProjection('api/groups/search/findNotParticipated', projection);
        return this.http.get(URL, {headers: this.getAuthHeaders()});
    }

    public get(href: string, projection?: string): Observable<any> {
        let URL = UrlUtils.removePrefix(href);
        URL = UrlUtils.addProjection(URL, projection);
        return this.http.get(URL, {headers: this.getAuthHeaders()}).map(res => res.json());
    }

    private getAuthHeaders(): Headers {
        return this.authService.getAuthorizationHeaders();
    }

    public delete(id: number): Observable<Response> {
      let URL = `/api/groups/${id}`;
      return this.http.delete(URL, {headers: this.getAuthHeaders()}).map(res => res.json());
    }

}
