export interface RepeatableTest {
    clearContext();
    initSamplesOrder();
    onSampleSelectToPlay(sampleKey :string);
    onSubmit();
}