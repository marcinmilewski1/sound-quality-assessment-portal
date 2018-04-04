import {Injectable} from '@angular/core';
import {Response} from "@angular/http";
import {CustomError} from "./CustomError";

@Injectable()
export class ErrorResolverService {

    constructor() {
    }

    public resolve(response: Response): CustomError {
        let error = this.errors.find((e) => e.status === response.status);
        if (error == null) {
            return new CustomError("Error: " + response.status, JSON.stringify(response.json()));
        }
        else {
            return new CustomError(error.title, error.content);
        }
    }

    private errors = [
        {
            status: 401,
            title: "Unauthorized",
            content: "Please sign up"
        }
    ]

}
