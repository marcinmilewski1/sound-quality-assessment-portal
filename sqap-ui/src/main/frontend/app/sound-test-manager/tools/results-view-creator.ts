export class ResultsViewCreator {

    static createFriendlyResultsView(test: any): any {
        let view: any = {};
        view.name = test.name;
        view.type = test.type;
        view.instruction = test.description;
        view.samples = (test.samples as any[]).map((sample) => {
            return {
                key: sample.key,
                fileName: sample.fileName
            };
        });
        if (test.type === 'ABX') {
            return this.createFriendlyAbxResultsView(test, view);
        }
        else if (test.type === 'MUSHRA') {
            return this.createFriendlyMushraResultsView(test, view);
        }
        else throw new TypeError("Wrong type");
    }

    private static createFriendlyAbxResultsView(test: any, view: any): any {
        let testResultGroup = test.testResultGroups as any[];

        let resultGroupView = testResultGroup
            .map((resultGroup, groupIndex) => {
                    return {
                        index: groupIndex,
                        tester: resultGroup.createdBy,
                        creationDate: resultGroup.createdDate,
                        isHearingDefect: resultGroup.isHearingDefect,
                        age: resultGroup.age,
                        sex: resultGroup.sex,
                        results: resultGroup.testResults
                            .map((singleResult, resultIndex) => {
                                return {
                                    iteration: singleResult.iteration,
                                    result: {
                                        correct: singleResult.blindSampleKey,
                                        answer: singleResult.answer,
                                    }
                                }
                            })
                    };
                }
            );

        view.iterationGroup = resultGroupView;
        return view;
    }

    private static createFriendlyMushraResultsView(test: any, view: any): any {
        let testResultGroup = test.testResultGroups as any[];

        let resultGroupView = testResultGroup
            .map((resultGroup, groupIndex) => {
                    return {
                        index: groupIndex,
                        tester: resultGroup.createdBy,
                        creationDate: resultGroup.createdDate,
                        isHearingDefect: resultGroup.isHearingDefect,
                        age: resultGroup.age,
                        sex: resultGroup.sex,
                        results: resultGroup.testResults.map((testResult) => {
                            return {
                                iteration: testResult.iteration,
                                result: testResult.sampleResults
                                    .map((singleResult, resultIndex) => {
                                        return {
                                            sample: singleResult.key,
                                            value: singleResult.value
                                        };
                                    })
                            }
                        })
                    };
                }
            );

        view.iterationGroup = resultGroupView;
        return view;
    }
}
