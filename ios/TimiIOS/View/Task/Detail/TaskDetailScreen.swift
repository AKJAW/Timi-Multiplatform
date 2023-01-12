import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine

class TaskDetailPublisher: ObservableObject {
    private var cancellables = Set<AnyCancellable>()
    private var task: Task?
    var viewModel: TaskDetailViewModel?
    
    @Published
    var entries: [TimeEntryUi] = []
    
    func initialize(task: Task) {
        self.task = task
        let viewModel: TaskDetailViewModel =
            KoinWrapper.get(typeProtocol: TaskDetailViewModel.self, parameter: task.id as Any)
        self.viewModel = viewModel
        // TODO calendar day selection logic
        viewModel.addTimeEntry(
            timeAmount: TimestampMilliseconds(value: 360000),
            day: CalendarDay(day: 6, month: 1, year: 2022)
        )
        createPublisher(for: viewModel.getTimeEntriesNative(day: CalendarDay(day: 6, month: 1, year: 2022)))
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { completion in
                print("Received completion getTimeEntriesNative: \(completion)")
            }, receiveValue: { timeEntryUi in
                print("Received value getTimeEntriesNative: \(timeEntryUi)")
                self.entries = timeEntryUi
            })
            .store(in: &cancellables)
    }
}

struct TaskDetailScreen: View {
    
    let task: Task
    @ObservedObject private var publisher = TaskDetailPublisher()
    
    var body: some View {
        HStack {
            List(publisher.entries, id: \.id) { entry in
                TimeEntryItem(entry: entry, onRemoveClick: { publisher.viewModel?.removeTimeEntry(id: entry.id) })
            }
            .listStyle(PlainListStyle())
        }
        .onAppear(
            perform: { publisher.initialize(task: task) }
        )
    }
}

private struct TimeEntryItem : View {
    
    let entry: TimeEntryUi
    let onRemoveClick: () -> Void
    
    var body: some View {
        HStack {
            Text(entry.formattedTime)
            Spacer(minLength: 4.0)
            HStack {
                Text(entry.formattedDate)
                Image(systemName: "trash")
                    .font(.system(size: 18.0))
            }
        }
    }
}
