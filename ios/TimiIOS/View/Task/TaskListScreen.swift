import SwiftUI
import shared
import KMMViewModelSwiftUI
//import Combine
//import KMPNativeCoroutinesCombine
//
//class TaskListPublisher: ObservableObject {
//    private var cancellables = Set<AnyCancellable>()
//    let viewModel: TaskListViewModel =
//    KoinWrapper.get(typeProtocol: TaskListViewModel.self)
//
//    @Published
//    var tasks: [Task] = []
//
//    init() {
//        createPublisher(for: viewModel.tasksNative)
//            .receive(on: RunLoop.main)
//            .sink { completion in
//                print("Received completion availableTasksNative: \(completion)")
//            } receiveValue: { tasks in
//                self.tasks = tasks
//            }
//            .store(in: &cancellables)
//    }
//}

struct TaskListScreen: View {

    @State private var isDialogShown = false
    @StateViewModel var viewModel: TaskListViewModel =
        KoinWrapper.get(type: TaskListViewModel.self)

    var body: some View {
        NavigationView {
            List(viewModel.tasksNativeValue, id: \.id) { task in
                TaskListItem(
                    task: task,
                    onClick: {
                        viewModel.toggleTask(toggledTask: task)
                    }
                )
            }
            .listStyle(PlainListStyle())
            .addTaskDialog(
                isShowing: $isDialogShown,
                addTask: { taskName, taskColor in
                    viewModel.addTask(
                        taskToBeAdded: Task(
                            id: 0,
                            name: taskName,
                            backgroundColor: taskColor,
                            isSelected: false
                        )
                    )
                }
            )
            .toolbar {
                ZStack {
                    Image(systemName: "plus")
                }
                .onTapGesture {
                    isDialogShown = true
                }
            }
            .navigationTitle(Text("Tasks"))
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}


struct TaskListItem: View {

    let task: Task
    let onClick: () -> Void

    var body: some View {
        HStack {
            let fontColor = accessibleFontColor(taskColor: task.backgroundColor)
            Text(task.name)
                .padding(8)
                .frame(maxWidth: .infinity)
                .foregroundColor(fontColor)
            Spacer()
            let checkmarkBackground = task.isSelected ? task.backgroundColor.toSwiftColor() : Color.white
            let checkmarkColor = task.isSelected ? fontColor : Color.black.opacity(0.1)
            Image(systemName: "checkmark")
                .resizable()
                .scaledToFit()
                .frame(maxHeight: .infinity)
                .frame(width: 15)
                .padding([.vertical], 8)
                .padding([.horizontal], 16)
                .background(checkmarkBackground)
                .foregroundColor(checkmarkColor)
                .onTapGesture(perform: onClick)
        }
        .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
        .background(task.backgroundColor.toSwiftColor())
        .cornerRadius(8)
        .background(
            NavigationLink(destination: Text("Detail for \(task.id)")) {
                EmptyView()
            }
        )
//        .simultaneousGesture(LongPressGesture().onEnded({ _ in
//            onClick()
//        }))
    }
}

struct TaskListScreen_Previews: PreviewProvider {
    static var previews: some View {
        TaskListScreen()
    }
}
