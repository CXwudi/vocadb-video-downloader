###### *Code* with the _love_ and _passion_ of **初音ミク**, **ボカロ**, **アニメ** and **二次元**

[![alt text](https://i.imgur.com/yi3yLkX.png)](https://ec.crypton.co.jp/pages/prod/vocaloid/mikuv4x "初音ミクv4x")
[![alt text](https://upload.wikimedia.org/wikipedia/de/c/ce/NicoNicoDouga-Logo-Vector.svg)](https://www.nicovideo.jp/  "ニコニコ動画")

# Project Motivation

//TODO

# Introduction

Project VD: Video Downloader for Vocal Characters  
The successor of my previous project [Niconico Video Downloader](https://github.com/CXwudi/Niconico-Video-Downloader).

Aim to provide the following features:

* Support Vocaloid producers' common video website like Youtube, Niconico, Bilibili (and more if needed)
* Can get a list of PVs to download from VocaDB favourite list (or favourite list from supported websites)
* Able to download Vocaloid PV from supported websites
* Able to extract audio from Vocaloid PV and embed thumbnail and tags using information from VocaDB

The project is currently under development, current stages are:

1. ✅ common-entity (define entites obj for stroing information)
2. ✅ common-util (some util classes and function shared across all maven modules)
3. 🔄 vocadbpv-task-producer (read VocaDB favourite list and produce/update the pv task in json)
4. ❌ pv-downloader-util
5. ❌ vocadbpv-video-downloader (download PV base on pv task)
6. ❌ vocadbpv-postprocessor (extract audio and add tags, thumbnails)

# Appreciation

//TODO: complete

* [VocaDB](https://github.com/VocaDB/vocadb) and its RestAPI
* [youtube-dl](https://github.com/ytdl-org/youtube-dl)


