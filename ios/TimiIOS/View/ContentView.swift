//
//  ContentView.swift
//  TimiIOS
//
//  Created by Aleksander Jaworski on 04/04/2022.
//  Copyright © 2022 Touchlab. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    @State private var selection: Tab = .task
    
    enum Tab {
        case task
        case stopwatch
        case settings
    }
    
    var body: some View {
        TabView(selection: $selection) {
            TaskListScreen()
                .tabItem {
                    Label("Tasks", systemImage: "list.dash")
                }
                .tag(Tab.task)
            StopwatchListScreen()
                .tabItem {
                    Label("Timer", systemImage: "stopwatch")
                }
                .tag(Tab.stopwatch)
            Text("Settings")
                .tabItem {
                    Label("Settings", systemImage: "gear")
                }
                .tag(Tab.settings)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
