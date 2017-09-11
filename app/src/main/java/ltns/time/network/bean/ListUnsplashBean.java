package ltns.time.network.bean;

import java.util.List;

/**
 * Created by guyuepeng on 2017/5/26.
 * Email: gu.yuepeng@foxmail.com
 */

public class ListUnsplashBean {
        /**
         * id : dEhYjG2I6hY
         * created_at : 2017-05-25T17:25:25-04:00
         * updated_at : 2017-05-26T03:48:32-04:00
         * width : 5184
         * height : 3456
         * color : #FECD52
         * likes : 0
         * liked_by_user : false
         * user : {"id":"1NJcA4bjLsY","updated_at":"2017-05-26T03:48:32-04:00","username":"syth_rabbit","name":"SYTH Rabbit","first_name":"SYTH","last_name":"Rabbit","portfolio_url":null,"bio":"","location":null,"total_likes":0,"total_photos":1,"total_collections":0,"profile_image":{"small":"https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=0ad68f44c4725d5a3fda019bab9d3edc","medium":"https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=356bd4b76a3d4eb97d63f45b818dd358","large":"https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=ee8bbf5fb8d6e43aaaa238feae2fe90d"},"links":{"self":"https://api.unsplash.com/users/syth_rabbit","html":"http://unsplash.com/@syth_rabbit","photos":"https://api.unsplash.com/users/syth_rabbit/photos","likes":"https://api.unsplash.com/users/syth_rabbit/likes","portfolio":"https://api.unsplash.com/users/syth_rabbit/portfolio","following":"https://api.unsplash.com/users/syth_rabbit/following","followers":"https://api.unsplash.com/users/syth_rabbit/followers"}}
         * current_user_collections : []
         * urls : {"raw":"https://images.unsplash.com/photo-1495747487836-683426d37d0f","full":"https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=970b8ef815fd8c72b022ca94430479e1","regular":"https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=b3ce499b1e0a0038d70f3cd3b728f9ff","small":"https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=9fff4a0dbab636f153ad6afad86123c1","thumb":"https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=9273697783aa443126f5f462e109e7f2"}
         * categories : []
         * links : {"self":"https://api.unsplash.com/photos/dEhYjG2I6hY","html":"http://unsplash.com/photos/dEhYjG2I6hY","download":"http://unsplash.com/photos/dEhYjG2I6hY/download","download_location":"https://api.unsplash.com/photos/dEhYjG2I6hY/download"}
         */

        private String id;
        private String created_at;
        private String updated_at;
        private int width;
        private int height;
        private String color;
        private int likes;
        private boolean liked_by_user;
        private UserBean user;
        private UrlsBean urls;
        private LinksBeanX links;
        private List<?> current_user_collections;
        private List<?> categories;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public boolean isLiked_by_user() {
            return liked_by_user;
        }

        public void setLiked_by_user(boolean liked_by_user) {
            this.liked_by_user = liked_by_user;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public UrlsBean getUrls() {
            return urls;
        }

        public void setUrls(UrlsBean urls) {
            this.urls = urls;
        }

        public LinksBeanX getLinks() {
            return links;
        }

        public void setLinks(LinksBeanX links) {
            this.links = links;
        }

        public List<?> getCurrent_user_collections() {
            return current_user_collections;
        }

        public void setCurrent_user_collections(List<?> current_user_collections) {
            this.current_user_collections = current_user_collections;
        }

        public List<?> getCategories() {
            return categories;
        }

        public void setCategories(List<?> categories) {
            this.categories = categories;
        }

        public static class UserBean {
            /**
             * id : 1NJcA4bjLsY
             * updated_at : 2017-05-26T03:48:32-04:00
             * username : syth_rabbit
             * name : SYTH Rabbit
             * first_name : SYTH
             * last_name : Rabbit
             * portfolio_url : null
             * bio :
             * location : null
             * total_likes : 0
             * total_photos : 1
             * total_collections : 0
             * profile_image : {"small":"https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=0ad68f44c4725d5a3fda019bab9d3edc","medium":"https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=356bd4b76a3d4eb97d63f45b818dd358","large":"https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=ee8bbf5fb8d6e43aaaa238feae2fe90d"}
             * links : {"self":"https://api.unsplash.com/users/syth_rabbit","html":"http://unsplash.com/@syth_rabbit","photos":"https://api.unsplash.com/users/syth_rabbit/photos","likes":"https://api.unsplash.com/users/syth_rabbit/likes","portfolio":"https://api.unsplash.com/users/syth_rabbit/portfolio","following":"https://api.unsplash.com/users/syth_rabbit/following","followers":"https://api.unsplash.com/users/syth_rabbit/followers"}
             */

            private String id;
            private String updated_at;
            private String username;
            private String name;
            private String first_name;
            private String last_name;
            private Object portfolio_url;
            private String bio;
            private Object location;
            private int total_likes;
            private int total_photos;
            private int total_collections;
            private ProfileImageBean profile_image;
            private LinksBean links;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public Object getPortfolio_url() {
                return portfolio_url;
            }

            public void setPortfolio_url(Object portfolio_url) {
                this.portfolio_url = portfolio_url;
            }

            public String getBio() {
                return bio;
            }

            public void setBio(String bio) {
                this.bio = bio;
            }

            public Object getLocation() {
                return location;
            }

            public void setLocation(Object location) {
                this.location = location;
            }

            public int getTotal_likes() {
                return total_likes;
            }

            public void setTotal_likes(int total_likes) {
                this.total_likes = total_likes;
            }

            public int getTotal_photos() {
                return total_photos;
            }

            public void setTotal_photos(int total_photos) {
                this.total_photos = total_photos;
            }

            public int getTotal_collections() {
                return total_collections;
            }

            public void setTotal_collections(int total_collections) {
                this.total_collections = total_collections;
            }

            public ProfileImageBean getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(ProfileImageBean profile_image) {
                this.profile_image = profile_image;
            }

            public LinksBean getLinks() {
                return links;
            }

            public void setLinks(LinksBean links) {
                this.links = links;
            }

            public static class ProfileImageBean {
                /**
                 * small : https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=0ad68f44c4725d5a3fda019bab9d3edc
                 * medium : https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=356bd4b76a3d4eb97d63f45b818dd358
                 * large : https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=ee8bbf5fb8d6e43aaaa238feae2fe90d
                 */

                private String small;
                private String medium;
                private String large;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }
            }

            public static class LinksBean {
                /**
                 * self : https://api.unsplash.com/users/syth_rabbit
                 * html : http://unsplash.com/@syth_rabbit
                 * photos : https://api.unsplash.com/users/syth_rabbit/photos
                 * likes : https://api.unsplash.com/users/syth_rabbit/likes
                 * portfolio : https://api.unsplash.com/users/syth_rabbit/portfolio
                 * following : https://api.unsplash.com/users/syth_rabbit/following
                 * followers : https://api.unsplash.com/users/syth_rabbit/followers
                 */

                private String self;
                private String html;
                private String photos;
                private String likes;
                private String portfolio;
                private String following;
                private String followers;

                public String getSelf() {
                    return self;
                }

                public void setSelf(String self) {
                    this.self = self;
                }

                public String getHtml() {
                    return html;
                }

                public void setHtml(String html) {
                    this.html = html;
                }

                public String getPhotos() {
                    return photos;
                }

                public void setPhotos(String photos) {
                    this.photos = photos;
                }

                public String getLikes() {
                    return likes;
                }

                public void setLikes(String likes) {
                    this.likes = likes;
                }

                public String getPortfolio() {
                    return portfolio;
                }

                public void setPortfolio(String portfolio) {
                    this.portfolio = portfolio;
                }

                public String getFollowing() {
                    return following;
                }

                public void setFollowing(String following) {
                    this.following = following;
                }

                public String getFollowers() {
                    return followers;
                }

                public void setFollowers(String followers) {
                    this.followers = followers;
                }
            }
        }

        public static class UrlsBean {
            /**
             * raw : https://images.unsplash.com/photo-1495747487836-683426d37d0f
             * full : https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=970b8ef815fd8c72b022ca94430479e1
             * regular : https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=b3ce499b1e0a0038d70f3cd3b728f9ff
             * small : https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=9fff4a0dbab636f153ad6afad86123c1
             * thumb : https://images.unsplash.com/photo-1495747487836-683426d37d0f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=9273697783aa443126f5f462e109e7f2
             */

            private String raw;
            private String full;
            private String regular;
            private String small;
            private String thumb;

            public String getRaw() {
                return raw;
            }

            public void setRaw(String raw) {
                this.raw = raw;
            }

            public String getFull() {
                return full;
            }

            public void setFull(String full) {
                this.full = full;
            }

            public String getRegular() {
                return regular;
            }

            public void setRegular(String regular) {
                this.regular = regular;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }

        public static class LinksBeanX {
            /**
             * self : https://api.unsplash.com/photos/dEhYjG2I6hY
             * html : http://unsplash.com/photos/dEhYjG2I6hY
             * download : http://unsplash.com/photos/dEhYjG2I6hY/download
             * download_location : https://api.unsplash.com/photos/dEhYjG2I6hY/download
             */

            private String self;
            private String html;
            private String download;
            private String download_location;

            public String getSelf() {
                return self;
            }

            public void setSelf(String self) {
                this.self = self;
            }

            public String getHtml() {
                return html;
            }

            public void setHtml(String html) {
                this.html = html;
            }

            public String getDownload() {
                return download;
            }

            public void setDownload(String download) {
                this.download = download;
            }

            public String getDownload_location() {
                return download_location;
            }

            public void setDownload_location(String download_location) {
                this.download_location = download_location;
            }
        }
    }
