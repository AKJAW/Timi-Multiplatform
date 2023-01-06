import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine

class TaskDetailPublisher: ObservableObject {
    private var cancellables = Set<AnyCancellable>()
    private var task: Task?
    var viewModel: TaskDetailViewModel?
    
    init() {}
    
    func initialize(task: Task) {
        self.task = task
        self.viewModel = KoinWrapper.get(typeProtocol: TaskDetailViewModel.self, parameter: task.id as Any)
    }
}

struct TaskDetailScreen: View {
    
    let task: Task
    @ObservedObject private var publisher = TaskDetailPublisher()
    
    var body: some View {
        HStack {
            Text("Detail for \(task.id)")
        }
        .onAppear(
            perform: { publisher.initialize(task: task) }
        )
    }
}
