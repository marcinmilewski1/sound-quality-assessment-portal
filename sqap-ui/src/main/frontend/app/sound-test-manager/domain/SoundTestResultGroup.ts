import {SoundTestResult} from "./SoundTestResult";
import {AuditedEntity} from "./AuditedEntity";
export interface SoundTestResultGroup extends AuditedEntity {
    testResultEntities: SoundTestResult[];
}