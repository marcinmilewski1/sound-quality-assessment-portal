export const APP_MENU: AppMenuItem[] = [

  {
    name: 'Strona domowa',
    description: '',
    icon: 'public',
    link: ['']
  },
  {
    name: 'Kreator testów',
    description: 'Stwórz własną grupę testową',
    icon: 'my_library_add',
    link: ['test-creator'],
    roles: ['ROLE_USER']
  },
  {
    name: 'Odtwarzacz testów',
    description: 'Weź udział w testach odsłuchowych',
    icon: 'play_arrow',
    link: ['test-player']
  },
  {
    name: 'Menadżer testów',
    description: 'Zarządzaj swoimi testami, sprawdź i pobierz statystyki',
    icon: 'view_comfy',
    link: ['test-manager'],
    roles: ['ROLE_USER']

  },
  {
    name: 'O stronie',
    description: '',
    icon: 'person',
    link: ['about'],
  }
];

export interface AppMenuItem {
  name: string;
  description: string;
  icon: string;
  link: string[];
  roles?: string[];
}
