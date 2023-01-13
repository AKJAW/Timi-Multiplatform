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
    @State private var isDialogShown = false
    @ObservedObject private var publisher = TaskDetailPublisher()
    
    var body: some View {
        VStack {
            List {
                ForEach(publisher.entries, id: \.id) { entry in
                    TimeEntryItem(entry: entry, onRemoveClick: { publisher.viewModel?.removeTimeEntry(id: entry.id) })
                }
                HStack {
                    Spacer()
                    Text("Add a Time Entry")
                    Spacer()
                }.onTapGesture {
                    isDialogShown = true
                }
                .listStyle(PlainListStyle())
            }
        }
        .modifier(
            AddTimeEntryDialog(
                isShowing: $isDialogShown,
                addEntry: { timestampMilliseconds in
                    publisher.viewModel?.addTimeEntry(
                        timeAmount: timestampMilliseconds,
                        day: CalendarDay(day: 6, month: 1, year: 2022)
                    )
                }
            )
        )
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
                    .onTapGesture(perform: onRemoveClick)
            }
        }
    }
}
