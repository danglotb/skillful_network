import { CommentService } from './../../../../shared/services/comment.service';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { IComment } from './../../../../shared/mocks/comments.mock';;

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {
  @Input() comment: IComment;
  @Output() private commentDeleted = new EventEmitter<number>();
  public isViewable: boolean;



  constructor( public cmService : CommentService) { }

  ngOnInit(): void {
    { this.isViewable = true; }
  }

  public addVotes(){
    this.cmService.onUpVote(this.comment, 1);
  }
  public removeVotes(){
     this.cmService.onDownVote(this.comment, 1);
  }
  public async handleDelete() {
    let response = await this.cmService.deleteComment(this.comment.id);
    this.commentDeleted.emit();
 }
  public show() {
    this.isViewable = !this.isViewable;
 }

  getImage() {
    if (this.comment.user.profilePicture == null) {
      return 'https://www.gravatar.com/avatar/' + this.comment.user.id + '?s=128&d=identicon&r=PG'
    } else {
      return this.comment.user.profilePicture;
    }
  }

}
