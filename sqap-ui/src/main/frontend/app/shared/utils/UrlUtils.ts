export class UrlUtils {

    /* removes address and protocol prefix:
     Input: http://localhost:8090/api/groups
     Output: /api/groups
     */
    static removePrefix(url: string): string {
        let slashes = 0;
        let start = 0;
        for (let i = 0; i < url.length; i++) {
            if (slashes == 3) {
                start = i;
                break;
            }
            if (url.charAt(i) === '/') slashes++;
        }
        return url.slice(start, url.length);
    }

    static addProjection(url: string, projection: string) :string {
        if (projection != null) {
            if (url.charAt(url.length - 1) === '?') {
                return url.concat("projection=", projection);
            }
            else if(url.search("/\?")){ // has at least one parameter
                return url.concat("&projection=", projection);
            }
            else {
                return url.concat("?projection=", projection)
            }
        }
        else {
            return url;
        }
    }
}