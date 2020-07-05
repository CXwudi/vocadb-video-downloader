###### *Code* with the _love_ and _passion_ of **初音ミク**, **ボカロ**, **アニメ** and **二次元**

[![alt text](https://i.imgur.com/yi3yLkX.png)](https://ec.crypton.co.jp/pages/prod/vocaloid/mikuv4x "初音ミクv4x")
[![alt text](https://upload.wikimedia.org/wikipedia/de/c/ce/NicoNicoDouga-Logo-Vector.svg)](https://www.nicovideo.jp/  "ニコニコ動画")

# Project Motivation

//TODO

# Introduction

Project VD: Video Downloader for Vocal Characters  
A batching Vocaloid PV downloader driven by Youtube-dl, FFmpeg and Python Mutagen lib, that can download a list of Vocalid PVs, extract and tags these audio with information and thumbnails  
This is the successor of my previous project [Niconico Video Downloader](https://github.com/CXwudi/Niconico-Video-Downloader).

Aim to provide the following features:

* Support reading the favourite list in VocaDB, and produce/update the json formatted task file
  * favourite lists can be imported from youtube/niconico favourite list using the import feature in VocaDB website
  * Support of reading the favourite list directly from local file, Youtube, Niconico, Bilibili will be added in the future
* Can download Vocaloid PV from supported websites (Youtube, Niconico, Bilibili), and update the json formatted task file
* Can extract audio from Vocaloid PV and embed thumbnail and tags using information from VocaDB

The project is WIP, current progresses are:

1. ✅ [common-vocaloid-java](https://github.com/CXwudi/common-vocaloid-java) (implemented in another repo)
   1. ✅ common-entity (define entites obj for stroing information)
   2. ✅ common-util (some util classes and functions that reduce spolit code)
2. ✅ project-vd-common-util (some util classes and function that are only used by this project)
3. 🔄 vocadb-pv-task-producer (read VocaDB favourite list and produce/update the pv task and pv information in json)
4. ❌ [youtubedl-java](https://github.com/CXwudi/youtubedl-java) (forked from <https://github.com/sapher/youtubedl-java>, but need to modify it to suit our need)
5. ❌ vocadb-pv-downloader (fetch the video, thumbnail and tags of PVs base on pv task and pv information in json)
6. ❌ vocadb-pv-postprocessor (extract audio and add tags and thumbnails to audio files)

# Appreciation

I'd like to thanks for the following awesome project:

* [VocaDB](https://github.com/VocaDB/vocadb) and its RestAPI
* [youtube-dl](https://github.com/ytdl-org/youtube-dl)
