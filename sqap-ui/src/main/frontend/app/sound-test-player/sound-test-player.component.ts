import { Component, OnInit } from '@angular/core';
import {Headers, Http} from "@angular/http";
import {AuthService} from "../shared/auth/auth.service";
import {TestGroupGeneral} from "../sound-test-manager/domain/TestGroupGeneral";
import {TestGroupService} from "../shared/test/group/test-group.service";

@Component({
  selector: 'sqap-sound-test-player',
  templateUrl: './sound-test-player.component.html',
  styleUrls: ['./sound-test-player.component.scss']
})
export class SoundTestPlayerComponent implements OnInit {

  testGroups: any[];

  constructor(private testGroupService: TestGroupService) {}

  ngOnInit() {

    this.testGroupService.getNotParticipated("general")
        .subscribe(data => {
              this.testGroups = data.json()._embedded.testGroups;
            },
            err => {
              console.log("ERR");
            }
        );
  }

}

