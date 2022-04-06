import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine

class TaskListPublisher: ObservableObject {
    private var cancellables = Set<AnyCancellable>()
    let viewModel: TaskListViewModel =
    KoinWrapper.get(typeProtocol: TaskListViewModel.self)
    
    @Published
    var tasks: [Task] = []
    
    init() {
        createPublisher(for: viewModel.tasksNative)
            .receive(on: RunLoop.main)
            .sink { completion in
                print("Received completion availableTasksNative: \(completion)")
            } receiveValue: { tasks in
                self.tasks = tasks
            }
            .store(in: &cancellables)
    }
}

struct TaskListScreen: View {
    
    @State private var count = 0
    
    @ObservedObject private var publisher = TaskListPublisher()
    
    var body: some View {
        NavigationView {
            List(publisher.tasks, id: \.id) { task in
                Text(task.name)
            }
            .toolbar {
                ZStack {
                    Image(systemName: "plus")
                }
                .onTapGesture {
                    count = self.count + 1
                    publisher.viewModel.addTask(
                        taskToBeAdded: Task(
                            id: 0,
                            name: "Task \(count)",
                            backgroundColor: TaskColor(
                                red: Float.random(in: 0..<1),
                                green: Float.random(in: 0..<1),
                                blue: Float.random(in: 0..<1)
                            ),
                            isSelected: false
                        )
                    )
                }
            }
        }
    }
}

struct TaskListScreen_Previews: PreviewProvider {
    static var previews: some View {
        TaskListScreen()
    }
}
