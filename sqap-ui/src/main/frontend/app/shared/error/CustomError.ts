export class CustomError {
    private _tittle: string;
    private _content: string

    constructor(tittle: string, content: string) {
        this._tittle = tittle;
        this._content = content;
    }


    get tittle(): string {
        return this._tittle;
    }

    get content(): string {
        return this._content;
    }
}