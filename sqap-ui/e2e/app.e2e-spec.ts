import { SqapUiPage } from './app.po';

describe('sqap-ui App', function() {
  let page: SqapUiPage;

  beforeEach(() => {
    page = new SqapUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
