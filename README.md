# Network Programming Project

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

A network programming project showcasing various features for efficient network operations.

## Features

1. **Multi-Threaded SHA Digest:**
   - Efficiently compute SHA digests of files using multi-threading.
   - Ensure faster processing of large datasets with parallelized computations.

2. **Multi-Threaded Web Log Executor:**
   - Execute web logs concurrently to simulate realistic traffic scenarios.
   - Utilize multi-threading to handle multiple log entries simultaneously.

3. **INET Addressing:**
   - Implement INET addressing for robust network communication.
   - Ensure compatibility and standardization in network addressing.

4. **Concurrent Response Cache:**
   - Maintain a concurrent cache for storing and retrieving responses.
   - Optimize response time by handling cache operations concurrently.

5. **TCP 3-Way Handshake Implementation:**
   - Implemented the TCP 3-way handshake using the JnetPcap library for packet capture and analysis.
   - This feature enhances the project's capabilities by allowing detailed analysis of TCP connections from the network layer (L3).

## RFC Pre-requisites

This project adheres to the following RFCs for standardized network programming:

1. **RFC 791 - Internet Protocol (IP):**
   - Implementation of INET addressing follows the specifications outlined in RFC 791.

2. **RFC 1321 - The MD5 Message-Digest Algorithm:**
   - Multi-threaded SHA Digest utilizes the MD5 algorithm as defined in RFC 1321.

3. **RFC 7230 - Hypertext Transfer Protocol (HTTP/1.1):**
   - The Multi-Threaded Web Log Executor conforms to the HTTP/1.1 standard defined in RFC 7230.

4. **RFC 7234 - Hypertext Transfer Protocol (HTTP/1.1): Caching:**
   - The Concurrent Response Cache adheres to the caching guidelines specified in RFC 7234.

5. **RFC 793 - Transmission Control Protocol (TCP):**
   - The TCP 3-way handshake implementation follows the specifications outlined in RFC 793.

### Prerequisites for TCP 3-Way Handshake

Before using the TCP 3-way handshake feature, make sure to have the following prerequisites:

- JnetPcap library: [Link to JnetPcap](https://www.jnetpcap.com/) (this is similar to tap interface of linux kernel)

