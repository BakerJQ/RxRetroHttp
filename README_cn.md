# RxRetroHttp
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://jitpack.io/v/BakerJQ/RxRetroHttp.svg)](https://jitpack.io/#BakerJQ/RxRetroHttp)

Http请求库，支持同时存在多种返回格式和多个base url

文章介绍：https://www.bakerjq.com/2019/01/19/20190119_RxRetroHttp/

## 为何会存在此库
时不时总会出现这么一种情况，那就是一个app中存在多套api请求，比如需要请求不同域名下的接口、不同接口返回格式不一致、不同接口需要传不同的header等等

至于为啥会这样，那原因自然是不可告人的

RxRetroHttp的存在，就是为了让大家在这种情况下，能够通过更好的代码结构去实现http请求

## Gradle引用
在项目的根build.gradle中加入:
``` groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
在app或相应使用的module中加入
``` groovy
dependencies {
    implementation 'com.github.BakerJQ:RxRetroHttp:1.2.10'
}

```

## 如何使用
### 初始化
#### 主Url请求
在Application中初始化sdk，下面的代码展示了如何初始化，该初始化所绑定的请求方式为app主要的请求方式
```java
RxRetroHttp.init(this)
           .setBaseUrl("https://api.github.com/")//your main url
           .setDefaultErrMsg("Github开小差了")//default error hint message
           .setApiResultClass(GithubApiResult.class)//your main api result structure, if not, will use default gson converter
           .generateRetroClient()
```
#### 其他多套Api请求
如果你的app存在更多套的api请求方式，尝试下面的代码

注:
- 其他的Api初始化需要在init()之后
- 初始化时，不要忘了在generateRetroClient方法中加入相应的Tag
- 如果你不设置ApiResultClass, 那么库将会使用默认的GsonConverterFactory去解析返回数据，这意味着库本身将不会为你处理判断返回数据的逻辑，或者你可以设置自己的ResponseConverter
```java
RxRetroHttp.getInstance()
           .setApiResultClass(YourApiResult.class)//另一个的返回数据结构类
           .setBaseUrl("http://host/api/data/")//另一个url
           .generateRetroClient("YourTag");//指示该种API请求的Tag
```
#### 配置
如果你想要自定义http配置，你可以通过获取builder的方式或者直接调用set方法

注: 必须在init()和generateRetroClient()之间进行配置
```java
RxRetroHttp.init().setXXX().setXXX();
Retrofit.Builder retrofitBuilder = RxRetroHttp.getRetrofitBuilder();
retrofitBuilder.setXXX().setXXX();
OkHttpClient.Builder okHttpBuilder = RxRetroHttp.getOkHttpClientBuilder();
okHttpBuilder.setXXX().setXXX();
RxRetroHttp.getInstance().generateRetroClient();
//RxRetroHttp.getInstance().generateRetroClient("YourTag")
```

### Api请求
#### 第一步. 定义数据返回类
下面是简单的代码样例
```java
public class YourApiResult<T> implements IApiResult<T> {
    private int code;//返回code
    private String msg;//返回信息
    private T result;//返回数据
    //成功返回的情况(eg. code == 1)
    @Override
    public boolean isSuccess();
    //返回数据结构
    @Override
    public T getData();
    //返回提示信息
    @Override
    public String getResultMsg();
    //返回code
    @Override
    public String getResultCode();
    //返回数据在json中的key值
    @Override
    public String getDataField();
}
```
#### 第二步. 定义Retrofit Api Service
定义Retrofit ApiService, 值得注意的是, 你不需要使用完整的ApiResult包裹数据类的方式（比如说"Observable<YourApiResult< TestInfo >>"）作为返回，可以通过RetroTag注解标示tag
```java
@RetroTag("YourTag")
public interface YourApiService {
    @GET("test/info")
    Observable<TestInfo> getTestInfo();
    @GET("test/list")
    Observable<List<TestInfo>> getTestInfo();
}
```
#### 第三步. 调用
像Retrofit一样调用
```java
RxRetroHttp.create(YourApiService.class).getTestInfo()
```

### Mock数据
你可以通过json字符串以及文件的方式进行mock。

若要开启mock，请在初始化时进行设置。

```java
RxRetroHttp.init(this).setMockEnable(true)
```

#### 通过文件进行Mock
Step 1. 将mock文件放到assets下

Step 2. 对API方法增加Headers注解

```java
@Headers({RxRetroHttp.MOCK_FILE_PATH_KEY + ":mockfile.json")
@GET("test/info")
Observable<TestInfo> getTestInfo();
```

#### 通过Json字符串进行Mock
直接添加Headers

```java
@Headers({RxRetroHttp.MOCK_DATA_KEY + ":{\"testInfo\":\"info\"}")
@GET("test/info")
Observable<TestInfo> getTestInfo();
```

#### 所支持的Header Key

| key | 描述 |
| ---- | ---- |
| MOCK_DATA_KEY | mock json字符串 |
| MOCK_FILE_PATH_KEY | mock assets文件路径 |
| MOCK_ENABLE_KEY | 是否开启mock |
| MOCK_DELAY_KEY | mock返回结果的延时 |

## Android API等级小于21
对于Android API等级小于21的工程，按如下方式引入该库
``` groovy
dependencies {
    implementation ('com.github.BakerJQ:RxRetroHttp:1.2.0'){
        exclude group: 'com.squareup.okhttp3', module: 'okhttp'
    }
    implementation 'com.squareup.okhttp3:okhttp:3.12.0'
}
```

## 感谢
感谢（[RxEasyHttp](https://github.com/zhou-you/RxEasyHttp)）提供的思路

## *License*
RxRetroHttp is released under the Apache 2.0 license.

```
Copyright 2019 BakerJ.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at following link.

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
