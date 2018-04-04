export class ArrayUtils {
    static concatSafe(ar1: any[], ar2: any[]) : any[] {
        if (ar1 == null || ar1.length == 0) return ar2;
        if (ar2 == null || ar2.length == 0) return ar1;
        else return ar1.concat(ar2);
    }

    //https://bost.ocks.org/mike/shuffle/
    static shuffle(array : any[]): any[] {
    let copy = [], n = array.length, i;

    // While there remain elements to shuffle…
    while (n) {

        // Pick a remaining element…
        i = Math.floor(Math.random() * array.length);

        // If not already shuffled, move it to the new array.
        if (i in array) {
            copy.push(array[i]);
            delete array[i];
            n--;
        }
    }
    return copy;
}

}