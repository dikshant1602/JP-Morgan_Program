# JP Morgan Chase & Co. Software Engineering Virtual Experience

This repository contains my work for the **JPMorgan Chase & Co. Software Engineering Virtual Experience**, completed through the **Forage** platform.  
This simulation provided a practical introduction to the tools and methodologies used in a real-world financial technology environment.

---

## 📝 Project Overview
The goal of this virtual experience was to:
- Set up a development environment  
- Interface with a stock price data feed  
- Use JPMorgan's open-source library, **Perspective**, to visualize the data for traders  

The tasks involved a combination of **Python**, **React**, and **TypeScript** to build a web-based application that displays real-time financial data.

---

## 🛠️ Technologies Used
- **Programming Languages:** Python 3, TypeScript  
- **Frameworks/Libraries:** React, JPMorgan Chase Perspective  
- **Tools:** Git, Node.js/npm, Python virtual environments  

---

## 📌 Key Tasks Completed

### **Task 1: Interface with a Stock Price Data Feed**
**Objective:** Set up the local development environment and start the data feed server to prepare for data visualization.  

**Process:**
- Cloned the initial project repository containing the server-side logic and a basic React front-end.  
- Configured the Python environment and installed necessary dependencies from `requirements.txt`.  
- Launched the data server (`datafeed/server.py`) which simulates a live stream of stock prices.  
- Verified that the React application was running and ready for modification.  

---

### **Task 2: Implement JPMorgan's Perspective Library**
**Objective:** Modify the front-end application to use **Perspective** for advanced data visualization and analysis.  

**Process:**
- Installed `@finos/perspective-viewer` and `@finos/perspective-workspace` via npm.  
- Modified `App.tsx` to replace the basic HTML table with the `<perspective-viewer>` web component.  
- Configured the Perspective viewer to correctly load the data schema from the server (stock, top_ask_price, timestamp).  

---

### **Task 3: Display Data Visually for Traders**
**Objective:** Enhance the application to calculate and display meaningful financial metrics, such as the ratio between two stocks, and visualize this data in a real-time graph.  

**Process:**
- Implemented `getDataFromServer` in `DataStreamer.ts` to fetch and process simulated stock data.  
- Added logic in `Graph.tsx` to calculate the price ratio between two stocks (e.g., *ABC* and *DEF*).  
- Handled edge cases such as division by zero if a stock price became unavailable.  
- Updated the Perspective viewer schema to include the ratio field and configured the graph for continuous updates every 100ms.  

---

### **Task 4: (Bonus) Make an Enhancement Suggestion**
**Objective:** Identify a potential bug or area for improvement in the existing codebase and implement a fix.  

**Process:**
- **Identified Issue:** The trigger alert was based on a fixed threshold.  
- **Proposed Solution:**  
  - Implemented a function to calculate a historical average price for tracked stocks.  
  - Updated the alert system to trigger when the current price deviates by more than 10% from this average.  
  - This makes the alerting system more resilient to normal market volatility.  

---

## 💡 Key Learnings
- **Financial Data Handling:** Worked with real-time streaming and processing of financial data feeds.  
- **Full-Stack Integration:** Connected a Python-based data server with a React/TypeScript front-end.  
- **Open-Source Integration:** Successfully integrated and configured the **Perspective** library.  
- **Problem-Solving:** Improved debugging skills to resolve client-server communication issues.  
- **Practical Application:** Learned how web technologies are applied in a financial context to build trader tools.  

---

## ⚠️ Disclaimer
This project is a simulation created by **JPMorgan Chase & Co.** for **educational purposes** on the Forage platform.  
The code and data **do not represent real financial data, systems, or products**.  
