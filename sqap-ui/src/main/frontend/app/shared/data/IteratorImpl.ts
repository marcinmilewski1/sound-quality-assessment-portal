import {AsyncIteratorResult} from "./AsyncIteratorResult";
export class IteratorImpl implements Iterator<any> {

    private _pointer = 0;
    private _index = 0;
    constructor(private arr: any[]) {
    }

    next(value?: any): AsyncIteratorResult<any> {
        if (this._pointer < this.arr.length) {
            this._index = this._pointer;
            return {
                done: false,
                value: this.arr[this._pointer++],
                isReady: false,
                index: this.index
            };
        }
        else {
            return {
                done: true,
                value: null,
                isReady: false,
                index: this.index
            };
        }
    }


    get index(): number {
        return this._index;
    }
}