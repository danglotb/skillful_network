import { PublicationService } from 'src/app/shared/services/publication.service';
import { Publication } from './../../../../shared/models/application/publication';
import { CommentService } from './../../../../shared/services/comment.service';
import { User } from './../../../../shared/models/user/user';
import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from "@angular/forms";

@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.scss']
})
export class PublicationComponent implements OnInit {
  @Input() public publication : Publication;
  @Output() private upvote = new EventEmitter<number>();
  @Output() private downvote = new EventEmitter<number>();
  @Output() private delete = new EventEmitter<number>();
  @Output() private increase = new EventEmitter<number>();


  public type: string;
  public isViewable: boolean;
  isShow = false;
  value = '';
  hide = false;
  commentControl: FormControl;
  public formComment: FormGroup;

  constructor( private fb: FormBuilder, public commentService: CommentService, public pub: PublicationService ) { 
    console.log(this.publication);
    this._buildForm();
  }

  onSubmit() {
    console.log("ok");
    let comment =  {
      text : this.formComment.value["comment"],
      comments : [],
      user: null,
      votes: 0,
      dateOfComment : Date.now()
    }
    this.commentService.addComment(this.publication, comment);   
  }

  private _buildForm() {
    this.formComment = this.fb.group({
      comment: ["", [Validators.required, Validators.minLength(3)]],
    });
  }
  ngOnInit(): void {
    console.log(this.publication.comments);
    { this.isViewable = true; }
  }

  public handleUpVote() {
    this.upvote.emit(1);
  }
  public handleDownVote() {
    this.downvote.emit(1);
  }
  public handleDelete() {
    this.pub.deletePublication(this.publication.id);
  } 
  public IncreaseComments(){
    this.increase.emit(1);
    
 }
  toggleDisplay() {
    this.isShow = !this.isShow;
    this.hide = true;
  }
  public show() {
     this.isViewable = !this.isViewable;
  }

  public showComments(){
    this.hide = !this.hide;
  }
  getImage() {
    return "https://www.blog-nouvelles-technologies.fr/wp-content/uploads/2017/12/detective-avatar-icon-01--840x500.jpg";
  }

}
