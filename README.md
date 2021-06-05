
<h1 align="center">TMDB with MVVM</h1>
<p align="center">
 <a href="https://circleci.com/gh/yokeshezumalai/IndicatorSeekBarWithHint/tree/master"> <img src="https://circleci.com/gh/yokeshezumalai/IndicatorSeekBarWithHint/tree/master.svg?style=shield" /></a>
 <a href="https://jitpack.io/#yokeshezumalai/IndicatorSeekBarWithHint"><img src="https://jitpack.io/v/yokeshezumalai/IndicatorSeekBarWithHint/month.svg"/></a>
  <a href="https://jitpack.io/#yokeshezumalai/IndicatorSeekBarWithHint"> <img src="https://jitpack.io/v/yokeshezumalai/IndicatorSeekBarWithHint.svg" /></a>
 <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>
  <br /><br />
 </p>

# TMDB App - Mobile version of content Listing page developed with Kotlin / MVVM / Dependency Injection - Dagger.

- Accessed the Open Source TMDB API via RetroFit Network Calls.
- Used Dagger
- Created NetworkModule and implemented OkHttpClient inside that.
- Created a CustomOkHttpInterceptor - We can add a common header for all the API's here
- Used MVVM - Model View View Model - Fragment - ViewModel - Repo - API
- Used AppCache - Cached the data of each and every page using HashMap - key Value - Key will contains the page number and value contains the contents - Just to explain cache can be involved and used. I have implemented
  the cache to avoid the mulitple network calls. But again i just saved in the cache, i didn't use it, since its the one page application. But it can be used.
- Used NavGraph component.
- API Unit Testing using Mockito




https://github.com/yokeshezumalai/TMDB

### License
```

MIT License

Copyright (c) 2021 Yokesh Ezumalai

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
