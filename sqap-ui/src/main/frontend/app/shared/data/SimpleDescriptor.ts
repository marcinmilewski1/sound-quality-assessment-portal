export class SimpleDescriptor {
    private _id: number;

    toString() {
        return this.id;
    }


    constructor(id: number) {
        this._id = id;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }
}