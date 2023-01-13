import SwiftUI
import shared

struct AddTaskDialog: ViewModifier {

    @Binding var isShowing: Bool
    var addTask: (String, TaskColor) -> Void
    @State private var taskName: String = ""
    @State private var selectedColor: TaskColor = TaskColor(red: 255, green: 255, blue: 255)

    init(isShowing: Binding<Bool>, addTask: @escaping (String, TaskColor) -> Void) {
        _isShowing = isShowing
        self.addTask = addTask
    }

    func body(content: Content) -> some View {
        ZStack {
            content
            if isShowing {
                Rectangle().foregroundColor(Color.black.opacity(0.6))
                    .onTapGesture {
                        isShowing = false
                    }
                VStack(spacing: 16) {
                    TextField(
                        "Task name",
                        text: $taskName
                    )
                        .onSubmit { onSubmit() }
                    ColorPicker { color in
                        selectedColor = color
                    }
                    HStack {
                        Button("Add", action: { onSubmit() })
                    }
                }
                .padding()
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .foregroundColor(selectedColor.toSwiftColor()))
                .onTapGesture {}
                .padding(40)
            }
        }
    }

    func onSubmit() {
        print(taskName)
        isShowing = false
        addTask(
            taskName,
            selectedColor
        )
        taskName = ""
        selectedColor = TaskColor(red: 255, green: 255, blue: 255)
    }
}

struct ColorPicker: View {

    var onColorClick: (TaskColor) -> Void

    var body: some View {
        ScrollView(.horizontal) {
            LazyHStack(spacing: 0) {
                ForEach(AvailableColorsKt.availableTaskColors, id: \.self) { taskColor in
                    Text("")
                        .frame(width: 50, height: 50)
                        .background(taskColor.toSwiftColor())
                        .border(.white, width: 1)
                        .onTapGesture {
                            onColorClick(taskColor)
                        }
                }
            }
        }.frame(height: 50)
    }
}

extension View {
    func addTaskDialog(
        isShowing: Binding<Bool>,
        addTask: @escaping (String, TaskColor) -> Void
    ) -> some View {
        self.modifier(AddTaskDialog(isShowing: isShowing, addTask: addTask))
    }
}

// struct AddTaskDialog_Previews: PreviewProvider {
//    static var previews: some View {
//        AddTaskDialog(isShowing: .constant(true), presenting: self)
//    }
// }
