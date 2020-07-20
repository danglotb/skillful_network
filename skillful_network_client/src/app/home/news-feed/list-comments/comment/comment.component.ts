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
    return "https://www.blog-nouvelles-technologies.fr/wp-content/uploads/2017/12/detective-avatar-icon-01--840x500.jpg";
  }

}
