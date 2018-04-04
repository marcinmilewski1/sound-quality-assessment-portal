import {SoundTestResultGroup} from "./SoundTestResultGroup";
import {SoundTestGroup} from "./SoundTestGroup";
import {AuditedEntity} from "./AuditedEntity";
export interface SoundTest extends AuditedEntity{
    orderNumber: number;
    name: string;
    description: string;
    type: string;
    testResultGroupEntity: SoundTestResultGroup;
}