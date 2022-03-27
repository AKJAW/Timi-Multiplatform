import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine

class StopwatchListPublisher: ObservableObject {
    private var cancellables = Set<AnyCancellable>()
    let timerViewModel: StopwatchViewModel =
    KoinWrapper.get(type: StopwatchViewModel.self)
    
    @Published
    var availableTasks: [Task] = []
    
    @Published
    var stopwatches: [Task : String] = [:]
    
    init() {
        stopwatches = timerViewModel.stopwatchesNativeValue
        createPublisher(for: timerViewModel.availableTasksNative)
            .sink { completion in
                print("Received completion availableTasksNative: \(completion)")
            } receiveValue: { tasks in
                self.availableTasks = tasks
            }
            .store(in: &cancellables)
        
        createPublisher(for: timerViewModel.stopwatchesNative)
            .sink { completion in
                print("Received completion stopwatchesNative: \(completion)")
            } receiveValue: { stopwatches in
                self.stopwatches = stopwatches
            }
            .store(in: &cancellables)
    }
    
    func addStopwatch() {
        guard let task = availableTasks.first else { return }
        print("Adding: \(task)")
        timerViewModel.start(task: task)
    }
}

struct StopwatchListScreen: View {
    
    @ObservedObject private var publisher = StopwatchListPublisher()
    
    var body: some View {
        List {
            ForEach(
                Array(publisher.stopwatches.keys).sorted { $0.id < $1.id },
                id: \.self
            ) { task in
                StopwatchItem(task: task, time: publisher.stopwatches[task]!)
            }
            Text("Add stopwatch")
                .onTapGesture {
                    publisher.addStopwatch()
                }
        }
    }
}

struct StopwatchItem: View {
    var task: Task
    var time: String
    
    var body: some View {
        HStack {
            Text(task.name)
            Text(time)
        }
    }
}


struct StopwatchListScreen_Previews: PreviewProvider {
    static var previews: some View {
        StopwatchListScreen()
    }
}
