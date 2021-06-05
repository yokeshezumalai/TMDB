
<h1 align="center">TMDB with MVVM</h1>
<p align="center">
 <a href="https://circleci.com/gh/yokeshezumalai/IndicatorSeekBarWithHint/tree/master"> <img src="https://circleci.com/gh/yokeshezumalai/IndicatorSeekBarWithHint/tree/master.svg?style=shield" /></a>
 <a href="https://jitpack.io/#yokeshezumalai/IndicatorSeekBarWithHint"><img src="https://jitpack.io/v/yokeshezumalai/IndicatorSeekBarWithHint/month.svg"/></a>
  <a href="https://jitpack.io/#yokeshezumalai/IndicatorSeekBarWithHint"> <img src="https://jitpack.io/v/yokeshezumalai/IndicatorSeekBarWithHint.svg" /></a>
 <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>
  <br /><br />
 </p>

# TMDB App - Mobile version of content Listing page developed with Kotlin / MVVM / Dependency Injection - Dagger. A simple Android App that displays movies from TMDb with search option.

- Accessed the Open Source TMDB API via RetroFit Network Calls.
- Used Dagger
- Created NetworkModule and implemented OkHttpClient inside that.
- Created a CustomOkHttpInterceptor - We can add a common header for all the API's here
- Used MVVM - Model View View Model - Fragment - ViewModel - Repo - API
- Live Data
- Used Data Binding for views
- Used AppCache - Cached the data of each and every page using HashMap - key Value - Key will contains the page number and value contains the contents - Just to explain cache can be involved and used. I have implemented
  the cache to avoid the mulitple network calls.
- Used Navigation Graph component.
- API Unit Testing using Mockito.
- First fragment will display all the movies , and also details of each movies wil be displayed in the next fragment by clicking on that.
- Accessed the Genre API and saved it in cache - so that for each and every movie , we can access and filter and display the names
- Also Search Feature added - User can able to search the movies using the search icon in the tool bar, search will be triggered once the user enters 3 characters.




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
