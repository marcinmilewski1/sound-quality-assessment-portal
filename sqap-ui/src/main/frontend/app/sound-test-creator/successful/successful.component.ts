import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'test-creator-successful',
  templateUrl: './successful.component.html',
  styleUrls: ['./successful.component.scss']
})
export class SuccessfulComponent implements OnInit {
  private participationUrl: string;
  private managerUrl: string;
  private copyParticipationUrl: string;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    let id = this.route.snapshot.params['id'];
    this.participationUrl = `/test-player/player/${id}`;
    this.managerUrl = `/test-manager/group/${id}`;
    let currentOrigin = window.location.origin;
    console.log(currentOrigin);
    this.copyParticipationUrl = currentOrigin + this.participationUrl;
  }

  onSuccess() {
    console.log("OK");
  }
}
