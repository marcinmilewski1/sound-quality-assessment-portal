export class StatUtils {
    static abxPearsonsChiSquaredValue(correctAnswers: number, totalAnswers: number) {
        return (4 * Math.pow(correctAnswers - (totalAnswers / 2), 2)) / totalAnswers;
    }

    static getChiSquaredDistributionValues1DF() {
        return {
            0.05: 3.841,
            0.01: 6.635
        };
    }

}