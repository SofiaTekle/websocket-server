# 🔌 WebSocket Server

Jakarta EE WebSocket-server som hanterar realtidskommunikation för AppUser-applikationen.

## Funktioner

- WebSocket endpoint på /ws/chat
- Broadcastar meddelanden till alla anslutna klienter
- Loggning av anslutningar och fel

## Tekniker

- Jakarta EE 10
- Jakarta WebSocket API
- GlassFish

## Krav

- GlassFish
- AppUser-applikationen körs parallellt

## Context root

/websocket-server