import {SoundTest} from "./SoundTest";
import {AuditedEntity} from "./AuditedEntity";
export interface SoundTestGroup extends AuditedEntity{
    description: string;
    name: string;
    finished: boolean;
    createdDate: any;
    type: string;
    tests: SoundTest[];
}