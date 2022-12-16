import SwiftUI
import shared

struct AddStopwatchDialog: ViewModifier {
    
    @Binding private var isShowing: Bool
    var addStopwatch: (Task) -> Void
    @Binding private var tasks: [Task]
    
    init(isShowing: Binding<Bool>, tasks: Binding<[Task]>, addStopwatch: @escaping (Task) -> Void) {
        _isShowing = isShowing
        _tasks = tasks
        self.addStopwatch = addStopwatch
    }
    
    func body(content: Content) -> some View {
        ZStack {
            content
            if isShowing {
                Rectangle().foregroundColor(Color.black.opacity(0.6))
                    .onTapGesture {
                        isShowing = false
                    }
                VStack(spacing: 8) {
                    ForEach(tasks, id: \.id) { task in
                        Text(task.name)
                            .frame(maxWidth: .infinity, alignment: .center)
                            .padding(16)
                            .background(
                                RoundedRectangle(cornerRadius: 8)
                                    .foregroundColor(task.backgroundColor.toSwiftColor())
                            )
                            .onTapGesture {
                                addStopwatch(task)
                                isShowing = false
                            }
                    }
                }
                .frame(width: 250)
                .padding(16)
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .foregroundColor(Color.white)
                )
                .onTapGesture {}
            }
        }
    }
}

extension View {
    func addStopwatchDialog(
        isShowing: Binding<Bool>,
        tasks: Binding<[Task]>,
        addStopwatch: @escaping (Task) -> Void
    ) -> some View {
        self.modifier(AddStopwatchDialog(isShowing: isShowing, tasks: tasks, addStopwatch: addStopwatch))
    }
}
