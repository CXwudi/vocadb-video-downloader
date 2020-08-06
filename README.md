# Project-VD

## *Code* with the _love_ and _passion_ of **初音ミク**, **ボカロ**, **アニメ** and **二次元**

[![alt text](https://i.imgur.com/yi3yLkX.png)](https://ec.crypton.co.jp/pages/prod/vocaloid/mikuv4x "初音ミクv4x")
[![alt text](https://upload.wikimedia.org/wikipedia/de/c/ce/NicoNicoDouga-Logo-Vector.svg)](https://www.nicovideo.jp/  "ニコニコ動画")

## Project Motivation

//TODO

## Introduction

Project VD: Video Downloader for Vocal Characters  
A batching Vocaloid PV downloader driven by Youtube-dl, FFmpeg and Python Mutagen lib, that can download a list of Vocalid PVs, extract and tags these audio with information and thumbnails  
This is the successor of my previous project [Niconico Video Downloader](https://github.com/CXwudi/Niconico-Video-Downloader).

Aim to provide the following features:

* Support reading the favourite list in VocaDB, and produce/update a folder of json files containing all necessary information about these songs
  * the json file is simply of GET [`https://vocadb.net/api/songs/<songId>?fields=PVs`](https://vocadb.net/swagger/ui/index#!/SongApi/SongApi_GetById)
  * favourite lists can be imported from youtube/niconico favourite list using the import feature in VocaDB website
  * If I have time, I'll add supports of reading favourite lists directly from local file, Youtube, Niconico, Bilibili
* Can download Vocaloid PV from supported websites (Youtube, Niconico, Bilibili) base on json file
* Can extract audio from Vocaloid PV and embed thumbnail and tags using information from json files

## Current Progresses

The project is WIP:

1. ✅ [common-vocaloid-java](https://github.com/CXwudi/common-vocaloid-java) (implemented in another repo)
   1. ✅ common-entity (define entites obj for stroing information)
2. ✅ project-vd-common-util (some util classes and function that reduce spolier codes)
3. ✅ project-vd-common-entity (some entity classes that only project-vd used)
4. ✅ vocadb-pv-task-producer (read VocaDB favourite list and dump song info in json format to the output directory)
5. ✅ [youtubedl-java](https://github.com/CXwudi/youtubedl-java) (forked from <https://github.com/sapher/youtubedl-java>, but need to modify it to suit our need)
6. 🔄 vocadb-pv-downloader (take the output directory from vocadb-pv-task-producer as input, download PVs, thumbnails base on song info and save them in another output directory)
7. ❌ vocadb-pv-postprocessor (take the output directory from vocadb-pv-downloader as input, extract audios to another output directory, and add tags and thumbnails to these audio files)

## Appreciation

I'd like to thanks for the following awesome projects.
Without them, this project can't exist:

* [VocaDB](https://github.com/VocaDB/vocadb) and its [RestAPI](https://vocadb.net/swagger/ui/index#/)
* [youtube-dl](https://github.com/ytdl-org/youtube-dl)
* [youtubedl-java](https://github.com/sapher/youtubedl-java)
