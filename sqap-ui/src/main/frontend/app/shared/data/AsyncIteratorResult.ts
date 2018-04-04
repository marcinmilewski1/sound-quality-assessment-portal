export interface AsyncIteratorResult<T> extends IteratorResult<T> {
    index: number;
    done: boolean;
    value: T;
    isReady: boolean;
}