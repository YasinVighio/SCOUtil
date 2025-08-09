# 🎧 SCOUtil — Bluetooth Mic Enabler for Android

**SCOUtil** is a *vibe-coded* Android utility that lets you enable or disable **Bluetooth SCO (Synchronous Connection-Oriented) mode** system-wide.  
This can help voice recorders and certain messaging apps use your Bluetooth headset microphone even if they don’t officially support it.

---

## ✨ Features
- **One-Tap SCO Control** — turn your Bluetooth mic on or off for *everything*  
- **Foreground Service** keeps SCO alive until you say stop  
- Clean, minimal UI with **Start / Stop** buttons  
- Works on Android 11+, tested on real devices  
- *Vibe coded* for simplicity — no 2000-line spaghetti 🍜  

---

## 📌 How it Works
Normally, Bluetooth SCO is only active during calls.  
This app uses Android’s **`MODIFY_AUDIO_SETTINGS`** permission to start SCO globally,  
so other apps can pick up your Bluetooth mic audio without thinking you’re on a call.

---

## ⚠ Disclaimer & Safety
- **Not shady** — no hacking, no injection, no tampering with other apps.  
- Does **not** record anything on its own.  
- Permissions used are standard Android audio routing permissions:  
  - `RECORD_AUDIO` — needed so the system lets us route mic input  
  - `BLUETOOTH_CONNECT` — required from Android 12+ to talk to Bluetooth devices  
  - `MODIFY_AUDIO_SETTINGS` — to actually toggle SCO mode  
- Bluetooth mic routing still depends on your device’s firmware.

---

## 🚀 Usage
1. **Grant** the requested permissions  
2. **Connect** your Bluetooth headset/earbuds  
3. Tap **Start SCO** to enable Bluetooth mic system-wide  
4. Keep the service running while using your desired app  
5. Tap **Stop SCO** to turn it off  

---

## 📜 License
MIT License — free to use, fork, vibe, and improve.

---
