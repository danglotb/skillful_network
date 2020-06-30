import { Component, OnInit, Input, TemplateRef } from '@angular/core';

@Component({
  selector: 'app-login-view-component',
  templateUrl: './login-view.component.html',
  styleUrls: ['./login-view.component.scss']
})
export class LoginViewComponent implements OnInit {

  @Input() title:string;
  @Input() icon:string;
  @Input() public loginRef: TemplateRef<any>;
  
  constructor() { }

  ngOnInit(): void {
  }

}
