# File-Compressor-Huffman-Encoding

## Table Of Contents
- Overview
- Core Concepts
- Languages and Libraries
- Demo 


## Overview

In this project Huffman Encoding will be used to compress large text files. Said files will also be decoded without any text corruption or data loss. The goal of this project is to reduce file sizes by a significant amount via the huffman encoding scheme in order to reduce memory usage. The motivation behind this project is to get some more exposure working with greedy algortihms, file parsing, serialization, and compression.

## Core Concepts

- Data Strucutres (trees, arrays, hashtables, etc.)
- Algorithms (greedy, sorting, DFS, etc.)
- Serialization
- File Compression/Memory Efficiency
- Text Parsing
- Binary Encoding of text

## Languages and Libraries
- Java
- Java.util*
- Java.io.Serializable
- Java.io.FileInputStream
- Java.io.FileOutputStream
- Java.io.ObjectInputStream

## Demo (warandpeace.txt, 3190 KB .txt file)

- Below are the original .txt file's properties:

<img width="265" alt="image" src="https://github.com/Muslim-Rana/File-Compressor-Huffman-Encoding/assets/125168332/40057ccf-1934-4316-8d8e-a5e04fef5f11">

- After running Encode.java we produce the following 2 new files (File.encoded and Tree.ser) with a combined size of 1817 KB (**57% the size of the original file**):

<img width="524" alt="image" src="https://github.com/Muslim-Rana/File-Compressor-Huffman-Encoding/assets/125168332/7df9a9fd-ed48-40e5-b455-9a1eb5bc5cd4">

- After running Decode.java we produce the following file (File.decoded) which is exactly warandpeace.txt:

<img width="446" alt="image" src="https://github.com/Muslim-Rana/File-Compressor-Huffman-Encoding/assets/125168332/f7bc6186-e5d9-4c42-81a0-32aa5327046f">



