export class SimpleEvent {
    id: string
    data: any
    status: number
    statusText: string

    constructor(cid: string, data: any, status?: number, statusText?: string) {
        this.id = cid;
        this.data = data;
        this.status = status;
        this.statusText = statusText;
    }


}