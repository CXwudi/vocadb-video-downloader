# Project VD

## *Code* with the _love_ and _passion_ of **初音ミク**, **ボカロ**, **アニメ** and **二次元**

[![alt text](https://upload.wikimedia.org/wikipedia/de/c/ce/NicoNicoDouga-Logo-Vector.svg)](https://www.nicovideo.jp/  "ニコニコ動画")
[![alt text](https://i.loli.net/2020/08/13/LcM7GFqzHb2WuoS.png)](https://ec.crypton.co.jp/pages/prod/vocaloid/mikuv4x "初音ミクv4x")

## Project Motivation

I, am a Super Miku fan 😎 who like to check Vocaloid Daily Ranking on NicoNico or Youtube, and extract thoes songs from the PVs on Niconico or Youtube. 😁  
However, it takes too much time for me to keep downloading these PVs while checking ranking. 😭  
As a computer science student, can we write a program that automatically download PVs for me? 🤔  
The answer is Yes. 🤩  
In 2017, The predecessor of this project [Niconico Video Downloader](https://github.com/CXwudi/Niconico-Video-Downloader) was out. It can automatically download every PVs in my NicoNico favourite list just in one click. 😂  
However, as more and more Vocaloid Producers tend to abundant NicoNico and upload new songs to Youtube only (Yes! I am talking about you, HoneyWork and ナユタン星人) 😡, we need a new program that is extendable for support more video services (inclduing NicoNico, Youtube and Bilibili at least) 😏. Also we want to integrate an audio extractor in to the new program for automatically extracts audios from PVs that downloaded. 😄  
And this new program is VocaDB Video Downloader, in short called Project VD ✨

## Introduction

Project VD: [VocaDB](https://vocadb.net/) Video Downloader  
A multi-module program driven by Youtube-dl, FFmpeg and Python Mutagen library,
that can download PVs from various 二次元 websites (Niconico, Youtube, Bilibili and etc) according to a VocaDB favourite list, extract audios from PVs, and tags audios with dedicated information from VocaDB  

### Functionality of each modules

* **vocadb-pv-task-producer**:
  * Input: an ID of a favourite list in VocaDB
    * you can transform your Youtube/Niconico favourite list into VocaDB favourite list by using the import feature on the website
  * Output: a folder of json files representing detail information of Vocaloid songs in favourite list
    * the format of the json file follows GET [`https://vocadb.net/api/songs/<songId>?fields=PVs`](https://vocadb.net/swagger/ui/index#!/SongApi/SongApi_GetById "VocaDB Api Doc Page")(click to see the api doc page)  
    * if I have time, I can implement other modules for reading favourite lists directly from local file, Bilibili and writing same output
* **vocadb-pv-downloader**:
  * Input: the output of vocadb-pv-task-producer
  * Output: a copy of the input of this module + PVs and thumbnails downloaded from supported websites (Youtube, Niconico, Bilibili)
    * powered by youtube-dl
* **vocadb-pv-extractor**:
  * Input: the output of vocadb-pv-downloader
  * Output: a folder with copies of json files + audio files extracted from PVs with embedded thumbnail and tags information
    * extraction is done by ffmpeg
    * thumbnail and tag are done by Python Mutagen library
    * the json files provide information of tags

### General Implementation Consideration

Of course, the following functional requirements are my first concern:

* able to download videos from Bilibili, Youtube and Niconico
* automatically extract audio losslessly using ffmpeg
* since VocaDB provides useful info of Vocaloid songs, and youtube-dl can download thumbnails, why not embed tags and thumbnails using python mutagen lib

Hence, we decide to make a Maven multi-module project. Each module handle one action (task perparing, downloading and extracting)

However, there are some other things I concern: (non-functional requirements)

* replacable binary files (youtube-dl, ffmpeg, and etc)
* modifiable programs behaviors
  * espacially the youtube-dl commands
* can stop and continue
* can inspect and modify the input/output manually, by users or by some other programs

Hence, we come up with easy hackable configs, easy hackable output/input. As all modules follow the pattern of: take some input and some config files, then it produces an output.  
Input files, output files, config files and executable files are all placed outside of the programs. So they are free to be read, edited or replaced by any ways 😁

## Current Progresses

The project is WIP, current progresses are:

1. ✅ [common-vocaloid-java](https://github.com/CXwudi/common-vocaloid-java) (implemented in another repo)
   1. ✅ common-entity (define entites obj for storing information, also includes the Java model to receive output from VocaDB RestAPI)
2. ✅ project-vd-common-util (some util classes and function that reduce spolier codes)
3. ✅ project-vd-common-entity (some entity classes that only project-vd used)
4. ✅ vocadb-pv-task-producer
5. ✅ [youtubedl-java](https://github.com/CXwudi/youtubedl-java) (a wrapper of youtube-dl in Java, forked from <https://github.com/sapher/youtubedl-java> with various useful modification to suit our need)
6. 🔄 vocadb-pv-downloader
   * it embedds youtube-dl executables compiled from my folked repo [here](https://github.com/CXwudi/youtube-dl-niconico-enhanced)  
7. ❌ vocadb-pv-extractor

## Appreciation

I'd like to thanks for the following awesome projects.
Without them, this project can't exist:

* [VocaDB](https://github.com/VocaDB/vocadb) and its [RestAPI](https://vocadb.net/swagger/ui/index#/)
* [youtube-dl](https://github.com/ytdl-org/youtube-dl)
* [youtubedl-java](https://github.com/sapher/youtubedl-java)
