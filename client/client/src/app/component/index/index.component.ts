import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";
import {UserService} from "../../services/user.service";
import {Post} from "../../models/Post";
import {User} from "../../models/User";
import {PostService} from "../../services/post.service";
import {CommentService} from "../../services/comment.service";
import {NotificationService} from "../../services/notification.service";
import {ImageUploadService} from "../../services/image-upload.service";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  posts!: Post[];
  user!: User;
  isUserDataLoaded =false;
  isPostDataLoaded = false;

  constructor(private tokenStorage: TokenStorageService,
              private userService: UserService,
              private postService: PostService,
              private commentService: CommentService,
              private notificationService: NotificationService,
              private imageUploadService: ImageUploadService) { }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe(data => {
        console.log(data);
        this.posts = data;
        this.getImagesToPost(this.posts);
        this.getCommentsToPost(this.posts);
        this.isPostDataLoaded = true;
      })

    this.userService.getCurrentUser().subscribe(data => {
      this.user =data;
      this.isUserDataLoaded = true;
    })
  }

  getImagesToPost(posts: Post[]): void {
    posts.forEach( p => {
      if (p.id != null) {
        this.imageUploadService.getProfileImageForPost(p.id).subscribe(data => {
          p.image = data.imageBytes;
        })
      }
    });
  }

  getCommentsToPost(posts: Post[]): void {
    posts.forEach( p => {
      if (p.id != null) {
        this.commentService.getCommentToPost(p.id).subscribe(data => {
          p.comments = data;
        })
      }
    })
  }

  likePost(id: number, postIndex: number) {
    const post = this.posts[postIndex];
    console.log(post);

    if(!post.usersLiked?.includes(this.user.username)) {
      this.postService.likePost(id, this.user.username).subscribe(() => {
        post.usersLiked?.push(this.user.username);
        this.notificationService.showSnackBar("Вы лайкнули пост")
      });
    } else {
      this.postService.likePost(id, this.user.username).subscribe(() => {
        const ind = post?.usersLiked?.indexOf(this.user?.username);
        if(<number>ind > -1) {
          post.usersLiked?.splice(<number>ind, 1);
        }
        this.notificationService.showSnackBar("Вы отменили лайк данному посту");
      });
    }
  }

  commentPost(msg: string, id: number, postIndex: number): void {
    const post = this.posts[postIndex];
    console.log(post);

    this.commentService.addCommentToPost(id, msg).subscribe(data => {
      console.log(data);
      post.comments?.push(data);
    });
  }

  formatImage(b: any): any {
    if(b == null) {
      return null;
    }

    return 'data:image/jpeg;base64,' + b;
  }
}
