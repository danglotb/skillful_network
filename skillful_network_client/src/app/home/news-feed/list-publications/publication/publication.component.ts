import { User } from './../../../../shared/models/user/user';
import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from "@angular/forms";

@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.scss']
})
export class PublicationComponent implements OnInit {
  @Input() public user: User;
  @Input() public texte: string;
  @Input() public votes: number;
  @Input() public fichier: string;
  @Input() public dateOfPost: Date;
  @Output() private upvote = new EventEmitter<number>();
  @Output() private downvote = new EventEmitter<number>();
  @Output() private delete = new EventEmitter<number>();
  public type: string;
  public isViewable: boolean;
  isShow = false;
  value = '';
  commentaireControl: FormControl;
  public formCommentaire: FormGroup;

  constructor( private fb: FormBuilder) { 
    this._buildForm();
  }
  onSubmit() {
   
  }

  private _buildForm() {
    this.formCommentaire = this.fb.group({
      commentaire: ["", [Validators.required, Validators.minLength(3)]],
    });
  }
  ngOnInit(): void {
    { this.isViewable = true; }
  }

  public handleUpVote() {
    this.upvote.emit(1);
  }
  public handleDownVote() {
    this.downvote.emit(1);
  }
  public handleDelete() {
    this.delete.emit();
  } 
  toggleDisplay() {
    this.isShow = !this.isShow;
  }
  public show() {
     this.isViewable = !this.isViewable;
  }
  getImage() {
    return "https://www.blog-nouvelles-technologies.fr/wp-content/uploads/2017/12/detective-avatar-icon-01--840x500.jpg";
  }

}
