# Project VD

## *Code* with the _love_ and _passion_ of **初音ミク**, **ボカロ**, **アニメ** and **二次元**

[![alt text](https://i.loli.net/2020/08/13/LcM7GFqzHb2WuoS.png)](https://ec.crypton.co.jp/pages/prod/vocaloid/mikuv4x "初音ミクv4x")
[![alt text](https://upload.wikimedia.org/wikipedia/de/c/ce/NicoNicoDouga-Logo-Vector.svg)](https://www.nicovideo.jp/  "ニコニコ動画")

## Project Motivation

I, am a Super Miku fan who like to check Vocaloid Daily Ranking on NicoNico or Youtube, and extract thoes songs from the PVs on Niconico or Youtube. 😁  
However, it takes too much time for me to keep downloading these PVs while checking ranking. 😭  
As a computer science student, can we write a program that automatically download PVs for me? 🤔
The answer is Yes. 😎  
In 2017, The predecessor of this project [Niconico Video Downloader](https://github.com/CXwudi/Niconico-Video-Downloader) was out. It can automatically download every PVs in my NicoNico favourite list just in one click. 😂  
However, as more and more Vocaloid Producers tend to abundant NicoNico and upload new songs to Youtube only (Yes! I am talking about you, HoneyWork and ナユタン星人) 😡, we need a new program that is extendable for support more video services (inclduing NicoNico, Youtube and Bilibili at least) 😏. Also we want to integrate an audio extractor in to the new program for automatically extracts audios from PVs that downloaded. 😄  
And this new program is VocaDB Video Downloader, in short called Project VD ✨

## Introduction

Project VD: [VocaDB](https://vocadb.net/) Video Downloader  
A multi-module program driven by Youtube-dl, FFmpeg and Python Mutagen lib,
that can download PVs from VocaDB favourite list, and extract and tags these audio with information and thumbnails  

Aim to provide the following features:

* Support reading the favourite list in VocaDB, and produce/update a folder of json files containing all necessary information about these songs
  * the json file is simply of GET [`https://vocadb.net/api/songs/<songId>?fields=PVs`](https://vocadb.net/swagger/ui/index#!/SongApi/SongApi_GetById "VocaDB Api Doc")
  * favourite lists can be imported from youtube/niconico favourite list using the import feature in VocaDB website
  * If I have time, I'll add supports of reading favourite lists directly from local file, Youtube, Niconico, Bilibili
* Can download Vocaloid PV from supported websites (Youtube, Niconico, Bilibili) base on json files
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
   * it embedds youtube-dl executables compiled from my folked repo [here](https://github.com/CXwudi/youtube-dl-niconico-enhanced)  
7. ❌ vocadb-pv-postprocessor (take the output directory from vocadb-pv-downloader as input, extract audios to another output directory, and add tags and thumbnails to these audio files)

## Appreciation

I'd like to thanks for the following awesome projects.
Without them, this project can't exist:

* [VocaDB](https://github.com/VocaDB/vocadb) and its [RestAPI](https://vocadb.net/swagger/ui/index#/)
* [youtube-dl](https://github.com/ytdl-org/youtube-dl)
* [youtubedl-java](https://github.com/sapher/youtubedl-java)
