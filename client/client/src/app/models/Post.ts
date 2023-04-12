import {Comment} from './Comment'

export interface Post {
  id?: number;
  username: string;
  title: string;
  desc: string;
  location: string;
  likes?: number;
  image?: File;
  usersLiked?: string[];
  comments?: Comment[];
}
